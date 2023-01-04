package su.sa1zer.bookparser.payload.dto;

import lombok.Builder;
import su.sa1zer.bookparser.entity.ParserType;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BookDto(Long id, String name, String desc, int year, String img, int pages,
                      Long ISBN, ParserType parsedFrom, LocalDate created, LocalDate updated,
                      List<AuthorDto> authors, List<GenreDto> genres, List<TagDto> tags) {
}
