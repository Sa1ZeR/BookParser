package su.sa1zer.bookparser.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import su.sa1zer.bookparser.entity.ParserType;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;
import su.sa1zer.bookparser.payload.response.MessageResponse;
import su.sa1zer.bookparser.service.BookService;
import su.sa1zer.bookparser.service.paerser.BaseParserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/books/")
public class BookController {

    private final BookService bookService;
    private final HashMap<ParserType, BaseParserService> PARSERS = new HashMap<>();

    public BookController(BookService bookService, List<BaseParserService> parsers) {
        this.bookService = bookService;

        for(BaseParserService p : parsers) {
            PARSERS.put(p.getParserType(), p);
        }
    }

    @PostMapping("parse/{parser}")
    public ResponseEntity<?> parse(@PathVariable ParserType parser) {
        BaseParserService parserService = PARSERS.get(parser);
        if(parserService == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Type %s not supported", parser));

        parserService.parseBooks();

        return ResponseEntity.ok(new MessageResponse(
                String.format("Books successfully parsed from %s", parser.name())));
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
