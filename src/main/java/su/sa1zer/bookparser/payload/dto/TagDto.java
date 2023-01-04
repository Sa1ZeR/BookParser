package su.sa1zer.bookparser.payload.dto;

import lombok.Builder;

@Builder
public record TagDto(Long id, String name) {
}
