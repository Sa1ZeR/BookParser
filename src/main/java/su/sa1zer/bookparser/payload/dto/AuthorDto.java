package su.sa1zer.bookparser.payload.dto;

import lombok.Builder;
import lombok.Value;

@Builder
public record AuthorDto(Long id, String name) {

}
