package su.sa1zer.bookparser.payload.dto.mapper;

import org.springframework.stereotype.Component;
import su.sa1zer.bookparser.entity.Tag;
import su.sa1zer.bookparser.payload.dto.TagDto;
import su.sa1zer.bookparser.payload.dto.mapper.Map;

@Component
public class TagMapper implements Map<Tag, TagDto> {
    @Override
    public TagDto map(Tag from) {
        return TagDto.builder()
                .id(from.getId())
                .name(from.getName()).build();
    }
}
