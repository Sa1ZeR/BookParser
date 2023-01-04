package su.sa1zer.bookparser.service.paerser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoogleBooksParserServiceTest {

    @Autowired
    private GoogleBooksParserService googleBooksParserService;

    @Test
    void parseBooks() {
        if(googleBooksParserService.executorService == null)
            googleBooksParserService.executorService = Executors.newFixedThreadPool(8);
        googleBooksParserService.parseBooks();
//        googleBooksParserService.executorService.shutdown();
//        try {
//            googleBooksParserService.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//
//
//        }
    }
}