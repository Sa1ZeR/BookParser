package su.sa1zer.bookparser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import su.sa1zer.bookparser.entity.Author;
import su.sa1zer.bookparser.payload.dto.AuthorDto;
import su.sa1zer.bookparser.payload.dto.mapper.AuthorMapper;
import su.sa1zer.bookparser.repostory.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorService implements CrudService<AuthorDto, Long> {

    private final AuthorRepository repository;
    private final AuthorMapper authorMapper;


    @Override
    @Transactional
    public AuthorDto create(AuthorDto entity) {
        Author author = Author.builder().name(entity.name()).build();

        return authorMapper.map(repository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return repository.save(entity);
                }).map(authorMapper::map).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Author with id %s not found", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AuthorDto findById(Long id) {
        return repository.findById(id).map(authorMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Author with id %s not found", id)));
    }

    @Override
    public List<AuthorDto> findAll() {
        return repository.findAll().stream().map(authorMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Author getOrCreateAuthor(String name) {
        Optional<Author> authorOptional = repository.findByName(name);
        if(authorOptional.isPresent()) return authorOptional.get();

        Author author = Author.builder().name(name).build();

        repository.save(author);
        return author;
    }
}
