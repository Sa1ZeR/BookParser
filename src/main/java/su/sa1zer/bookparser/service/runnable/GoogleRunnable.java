package su.sa1zer.bookparser.service.runnable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import su.sa1zer.bookparser.entity.ParserType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GoogleRunnable implements Runnable {

    private final Character character;
    private final String url;
    private final int resultPerPage;

    @Override
    public void run() {
//        int startIndex = 0;
//        while (true) {
//            HttpRequest request = null;
//            try {
//                request = HttpRequest.newBuilder().uri(
//                        new URI(String.format(url + "?q=%s&maxResults=%s&startIndex=%s", character, resultPerPage, startIndex))).GET().build();
//            } catch (URISyntaxException e) {
//                log.error(e.getMessage(), e);
//                return;
//            }
//            HttpClient client = HttpClient.newBuilder().build();
//            try {
//                HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
//                if (send.statusCode() != 200)
//                    break;
//
//                JsonObject asJsonObject = JsonParser.parseString(send.body()).getAsJsonObject();
//
//                if (asJsonObject.has("totalItems") && asJsonObject.get("totalItems").getAsInt() == 0)
//                    break;
//                if(asJsonObject.get("items") == null)
//                    break;
//
//                JsonArray items = asJsonObject.get("items").getAsJsonArray();
//                for (int i = 0; i < items.size(); i++) {
//                    JsonObject item = items.get(i).getAsJsonObject().get("volumeInfo").getAsJsonObject();
//                    String title = item.get("title").getAsString();
//                    String desc = item.get("description") != null ? item.get("description").getAsString() : null;
//                    int pageCount = item.get("pageCount") != null ? item.get("pageCount").getAsInt() : -1;
//
//                    List<String> genres = new ArrayList<>();
//                    String genre = item.get("categories") != null ? item.get("categories").getAsString() : null;
//                    if (genre != null && genre.length() <= 128)
//                        genres.add(genre);
//
//                    int year = item.get("publishedDate") != null ?
//                            Integer.parseInt(item.get("publishedDate").getAsString()
//                                    .replaceAll("\\?", "0").substring(0, 4)) : 1970;
//                    String img = item.has("imageLinks") ?
//                            item.get("imageLinks").getAsJsonObject().get("thumbnail").getAsString() : null;
//
//                    Long ISBN = item.get("industryIdentifiers") != null ? parseLong(item) : null;
//
//                    List<String> authors = new ArrayList<>();
//                    if (item.get("authors") != null) {
//                        JsonArray authorsArr = item.get("authors").getAsJsonArray();
//                        for (int j = 0; j < authorsArr.size(); j++) {
//                            authors.add(authorsArr.get(j).getAsString());
//                        }
//                    }
//
//                    singleExecutor.execute(() ->
//                            addBook(title, desc, year, img, pageCount, ISBN, ParserType.GOOGLE_BOOKS, authors,
//                                    genres, new ArrayList<>()));
//                }
//
//                startIndex += RESULT_PER_PAGE;
//            } catch (IOException | InterruptedException e) {
//                log.error(e.getMessage(), e);
//            }
//        }
    }
}
