package su.sa1zer.bookparser.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import su.sa1zer.bookparser.config.ThreadConfig;
import su.sa1zer.bookparser.entity.ParserType;
import su.sa1zer.bookparser.payload.response.MessageResponse;
import su.sa1zer.bookparser.service.BookService;
import su.sa1zer.bookparser.service.paerser.BaseParserService;

import java.util.HashMap;
import java.util.List;

@Service
public class ParserFacade {

    private final HashMap<ParserType, BaseParserService> PARSERS = new HashMap<>();
    private final BookService bookService;

    public ParserFacade(BookService bookService, List<BaseParserService> parsers) {
        this.bookService = bookService;

        for(BaseParserService p : parsers) {
            PARSERS.put(p.getParserType(), p);
        }
    }

    public ResponseEntity<?> startParse(ParserType parser) {
        BaseParserService parserService = PARSERS.get(parser);
        if(parserService == null)
            return ResponseEntity.badRequest().body(new MessageResponse(
                    String.format("Type %s not supported", parser)));

        ThreadConfig.GLOBAL_SES.execute(parserService::executeParse);

        return ResponseEntity.ok(new MessageResponse(
                String.format("Parser %s successfully started", parser.name())));
    }
}
