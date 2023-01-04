package su.sa1zer.bookparser.payload.dto;

import lombok.Builder;

@Builder
public record GenreDto(Long id, String name) {
}
