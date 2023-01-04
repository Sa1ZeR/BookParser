package su.sa1zer.bookparser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import su.sa1zer.bookparser.entity.Tag;
import su.sa1zer.bookparser.payload.dto.TagDto;
import su.sa1zer.bookparser.payload.dto.mapper.TagMapper;
import su.sa1zer.bookparser.repostory.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService implements CrudService<TagDto, Long> {

    private final TagRepository repository;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public TagDto create(TagDto dto) {
        Tag tag = Tag.builder().name(dto.name()).build();
        return tagMapper.map(tag);
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return repository.save(entity);
                }).map(tagMapper::map).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Tag with id %s not found", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TagDto findById(Long id) {
        return repository.findById(id).map(tagMapper::map).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Tag with id %s not found", id)));
    }

    @Override
    public List<TagDto> findAll() {
        return repository.findAll().stream().map(tagMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Tag getOrCreateTag(String name) {
        Optional<Tag> optionalTag = repository.findByName(name);
        if(optionalTag.isPresent()) return optionalTag.get();

        Tag tag = Tag.builder().name(name).build();
        repository.save(tag);

        return tag;
    }
}
