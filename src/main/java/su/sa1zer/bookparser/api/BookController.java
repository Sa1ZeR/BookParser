package su.sa1zer.bookparser.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.sa1zer.bookparser.entity.ParserType;
import su.sa1zer.bookparser.facade.ParserFacade;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;
import su.sa1zer.bookparser.service.BookService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books/")
@RequiredArgsConstructor
public class BookController {

    private final ParserFacade parserFacade;
    private final BookService bookService;

    @PostMapping("parse/{parser}")
    public ResponseEntity<?> parse(@PathVariable ParserType parser) {
        return parserFacade.startParse(parser);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.findById(Long.parseLong(id)));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("filter")
    public ResponseEntity<?> getAllWithFilter(@Valid BookFilterRequest request) {
        return ResponseEntity.ok(bookService.findAllWithFilter(request));
    }
}
