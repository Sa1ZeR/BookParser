package su.sa1zer.bookparser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import su.sa1zer.bookparser.entity.Genre;
import su.sa1zer.bookparser.entity.Tag;
import su.sa1zer.bookparser.payload.dto.GenreDto;
import su.sa1zer.bookparser.payload.dto.mapper.GenreMapper;
import su.sa1zer.bookparser.repostory.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreService implements CrudService<GenreDto, Long> {

    private final GenreRepository repository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreDto create(GenreDto dto) {
        Genre genre = Genre.builder().name(dto.name()).build();
        return genreMapper.map(repository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(Long id, GenreDto dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return repository.save(entity);
                }).map(genreMapper::map).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Genre with id %s not found", id)));
    }

    @Transactional
    public Genre getOrCreateGenre(String name) {
        Optional<Genre> optionalGenre = repository.findByName(name);
        if(optionalGenre.isPresent()) return optionalGenre.get();

        Genre genre = Genre.builder().name(name).build();
        repository.save(genre);

        return genre;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GenreDto findById(Long id) {
        return repository.findById(id).map(genreMapper::map).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Genre with id %s not found", id)));
    }

    @Override
    public List<GenreDto> findAll() {
        return repository.findAll().stream().map(genreMapper::map).collect(Collectors.toList());
    }
}
