package su.sa1zer.bookparser.service.paerser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseParserServiceTest {

    @Autowired
    GoogleBooksParserService googleBooksParserService;

    @Test
    void getYearFromString() {

        String year1 = "101-";
        String year2 = "10Xx";
        String year3 = "1-?x";
        String year4 = "1111";

        int yearFromString = googleBooksParserService.getYearFromString(year1);
        int yearFromString1 = googleBooksParserService.getYearFromString(year2);
        int yearFromString2 = googleBooksParserService.getYearFromString(year3);
        int yearFromString3 = googleBooksParserService.getYearFromString(year4);

        assertAll(() -> {
            assertEquals(1010, yearFromString);
            assertEquals(1000, yearFromString1);
            assertEquals(1000, yearFromString2);
            assertEquals(1111, yearFromString3);
        });
    }
}