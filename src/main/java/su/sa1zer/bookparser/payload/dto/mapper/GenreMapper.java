package su.sa1zer.bookparser.payload.dto.mapper;

import org.springframework.stereotype.Component;
import su.sa1zer.bookparser.entity.Genre;
import su.sa1zer.bookparser.payload.dto.GenreDto;

@Component
public class GenreMapper implements Map<Genre, GenreDto>{
    @Override
    public GenreDto map(Genre from) {
        return GenreDto.builder()
                .id(from.getId())
                .name(from.getName()).build();
    }
}
