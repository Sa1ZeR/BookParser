package su.sa1zer.bookparser.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import su.sa1zer.bookparser.entity.Book;
import su.sa1zer.bookparser.entity.ParserType;
import su.sa1zer.bookparser.payload.dto.BookDto;
import su.sa1zer.bookparser.payload.dto.mapper.BookMapper;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;
import su.sa1zer.bookparser.payload.response.MessageResponse;
import su.sa1zer.bookparser.repostory.BookRepository;
import su.sa1zer.bookparser.utils.QPredicates;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService implements CrudService<BookDto, Long> {

    private final BookRepository repository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookDto create(BookDto dto) {
        return null;
    }

    @Override
    @Transactional
    public BookDto update(Long id, BookDto dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    entity.setDesc(dto.desc());
                    entity.setYear(dto.year());
                    entity.setImg(dto.img());
                    entity.setPages(dto.pages());
                    entity.setISBN(dto.ISBN());
                    entity.setParsedFrom(dto.parsedFrom());
                    //entity.setAuthors(dto.authors());
                    return repository.save(entity);
                }).map(bookMapper::map).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Book with id %s not found", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BookDto findById(Long id) {
        return repository.findById(id).map(bookMapper::map).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Book with id %s not found", id)));
    }

    @Override
    public List<BookDto> findAll() {
        return repository.findAll().stream().map(bookMapper::map).collect(Collectors.toList());
    }

    public List<BookDto> findAllWithFilter(BookFilterRequest request) {
        return repository.findAllByFilter(request).stream().map(bookMapper::map).collect(Collectors.toList());
    }

    @Transactional
    public void save(Book book) {
        repository.save(book);
    }

    @Transactional
    public void saveAndFlush(Book book) {
        repository.saveAndFlush(book);
    }

    public boolean hasBookWithName(String name) {
        return repository.findByName(name).isPresent();
    }
}
