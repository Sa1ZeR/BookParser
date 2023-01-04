package su.sa1zer.bookparser.payload.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import su.sa1zer.bookparser.entity.Book;
import su.sa1zer.bookparser.payload.dto.BookDto;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper implements Map<Book, BookDto> {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final TagMapper tagMapper;

    @Override
    public BookDto map(Book from) {
        return BookDto.builder()
                .id(from.getId())
                .name(from.getName())
                .desc(from.getDesc())
                .year(from.getYear())
                .img(from.getImg())
                .pages(from.getPages())
                .ISBN(from.getISBN())
                .parsedFrom(from.getParsedFrom())
                .created(from.getCreated())
                .updated(from.getUpdated())
                .authors(from.getAuthors().stream().map(authorMapper::map).collect(Collectors.toList()))
                .genres(from.getGenres().stream().map(genreMapper::map).collect(Collectors.toList()))
                .tags(from.getTags().stream().map(tagMapper::map).collect(Collectors.toList()))
                .build();
    }
}
