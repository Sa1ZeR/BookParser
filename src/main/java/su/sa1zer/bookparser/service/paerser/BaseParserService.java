package su.sa1zer.bookparser.service.paerser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import su.sa1zer.bookparser.entity.*;
import su.sa1zer.bookparser.service.AuthorService;
import su.sa1zer.bookparser.service.BookService;
import su.sa1zer.bookparser.service.GenreService;
import su.sa1zer.bookparser.service.TagService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseParserService {

    private Pattern NUMBER_PATTERN = Pattern.compile("\\d*");

    protected static final ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
    protected final BookService bookService;
    protected final AuthorService authorService;
    protected final TagService tagService;
    protected final GenreService genreService;

    public ExecutorService executorService;

    @Value("${parsers.thread-pools}")
    private int threadCount;

    @PostConstruct
    public void onInit() {
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public abstract ParserType getParserType();

    public abstract void parseBooks();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected synchronized void addBook(String name, String desc, Integer year, String img, Integer pages, Long ISBN, ParserType type,
                           List<String> authors, List<String> genres, List<String> tags) {

        if(bookService.hasBookWithName(name))
            return;

        Book book = Book.builder()
                .name(name)
                .desc(desc)
                .year(year)
                .img(img)
                .pages(pages)
                .ISBN(ISBN)
                .parsedFrom(type)
                .authors(parseAuthors(authors))
                .genres(parseGenres(genres))
                .tags(parseTags(tags))
                .build();

        log.debug(String.format("Adding %s book", name));

        bookService.save(book);
    }

    @Transactional
    public Set<Author> parseAuthors(List<String> authorsList) {
        return authorsList.stream().map(authorService::getOrCreateAuthor).collect(Collectors.toSet());
    }

    @Transactional
    public Set<Tag> parseTags(List<String> tagList) {
        return tagList.stream().map(tagService::getOrCreateTag).collect(Collectors.toSet());
    }

    @Transactional
    public Set<Genre> parseGenres(List<String> genresList) {
        return genresList.stream().map(genreService::getOrCreateGenre).collect(Collectors.toSet());
    }

    /**
     * universal fix for getting date from specific string
     * @return date
     */
    public int getYearFromString(String date) {
        Matcher matcher = NUMBER_PATTERN.matcher(date);
        if(matcher.find()) {
            StringBuilder result = new StringBuilder(matcher.group());
            System.out.println(matcher.group());
            while (result.length() < 4) {
                result.append(0);
            }
            return Integer.parseInt(result.toString());
        }
        return 0;
    }

}
