package su.sa1zer.bookparser.payload.dto.mapper;

import org.springframework.stereotype.Component;
import su.sa1zer.bookparser.entity.Author;
import su.sa1zer.bookparser.payload.dto.AuthorDto;

@Component
public class AuthorMapper implements Map<Author, AuthorDto> {
    @Override
    public AuthorDto map(Author from) {
        return AuthorDto.builder()
                .id(from.getId())
                .name(from.getName()).build();
    }
}
