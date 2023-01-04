package su.sa1zer.bookparser.service.paerser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import su.sa1zer.bookparser.entity.ParserType;
import su.sa1zer.bookparser.service.AuthorService;
import su.sa1zer.bookparser.service.BookService;
import su.sa1zer.bookparser.service.GenreService;
import su.sa1zer.bookparser.service.TagService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class GoogleBooksParserService extends BaseParserService{

    private static final List<Character> ALPHA = new ArrayList<>();
    private static final String MAIN_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final int RESULT_PER_PAGE = 40;

    public GoogleBooksParserService(BookService bookService, AuthorService authorService,
                                    TagService tagService, GenreService genreService) {
        super(bookService, authorService, tagService, genreService);
    }

    @Override
    public ParserType getParserType() {
        return ParserType.GOOGLE_BOOKS;
    }

    @Override
    public void parseBooks() {
        //AtomicInteger amount = new AtomicInteger(0);
        for (Character character : ALPHA) {
            int startIndex = 0;
            while (true) {
                HttpRequest request = null;
                try {
                    request = HttpRequest.newBuilder().uri(
                            new URI(String.format(MAIN_URL + "?q=%s&maxResults=%s&startIndex=%s", character, RESULT_PER_PAGE, startIndex))).GET().build();
                } catch (URISyntaxException e) {
                    log.error(e.getMessage(), e);
                    return;
                }
                HttpClient client = HttpClient.newBuilder().build();
                try {
                    HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (send.statusCode() != 200)
                        break;

                    JsonObject asJsonObject = JsonParser.parseString(send.body()).getAsJsonObject();

                    if (asJsonObject.has("totalItems") && asJsonObject.get("totalItems").getAsInt() == 0)
                        break;
                    if(asJsonObject.get("items") == null)
                        break;

                    JsonArray items = asJsonObject.get("items").getAsJsonArray();
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject().get("volumeInfo").getAsJsonObject();
                        String title = item.get("title").getAsString();
                        String desc = item.get("description") != null ? item.get("description").getAsString() : null;
                        int pageCount = item.get("pageCount") != null ? item.get("pageCount").getAsInt() : -1;

                        List<String> genres = new ArrayList<>();
                        String genre = item.get("categories") != null ? item.get("categories").getAsString() : null;
                        if (genre != null && genre.length() <= 128)
                            genres.add(genre);

                        int year = item.get("publishedDate") != null ?
                                Integer.parseInt(item.get("publishedDate").getAsString()
                                        .replaceAll("\\?", "0").substring(0, 4)) : 1970;
                        String img = item.has("imageLinks") ?
                                item.get("imageLinks").getAsJsonObject().get("thumbnail").getAsString() : null;

                        Long ISBN = item.get("industryIdentifiers") != null ? parseLong(item) : null;

                        List<String> authors = new ArrayList<>();
                        if (item.get("authors") != null) {
                            JsonArray authorsArr = item.get("authors").getAsJsonArray();
                            for (int j = 0; j < authorsArr.size(); j++) {
                                authors.add(authorsArr.get(j).getAsString());
                            }
                        }

                        //amount.addAndGet(1);
                        singleExecutor.execute(() ->
                                addBook(title, desc, year, img, pageCount, ISBN, ParserType.GOOGLE_BOOKS, authors,
                                        genres, new ArrayList<>()));
                    }
                    startIndex += RESULT_PER_PAGE;
                } catch (IOException | InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    private Long parseLong(JsonObject item) {
        JsonArray industryIdentifiers = item.get("industryIdentifiers").getAsJsonArray();
        for (int j = 0; j < industryIdentifiers.size(); j++) {
            JsonObject identItem = industryIdentifiers.get(j).getAsJsonObject();
            if (identItem.get("type").getAsString().equals("ISBN_13")) {
                return identItem.get("identifier").getAsLong();
            }
        }
        return null;
    }

    static {
        for(int i = 'а'; i <= 'я'; i++) {
            ALPHA.add((char) i);
        }
        for(int i = 'a'; i <= 'z'; i++) {
            ALPHA.add((char) i);
        }
        for(int i = '0'; i <= '9'; i++) {
            ALPHA.add((char) i);
        }
    }

    @RequiredArgsConstructor
    public abstract class GoogleRunnable implements Runnable {
        public final Character character;
    }
}
