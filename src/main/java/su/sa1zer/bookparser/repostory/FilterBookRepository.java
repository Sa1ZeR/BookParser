package su.sa1zer.bookparser.repostory;

import org.springframework.data.jpa.repository.EntityGraph;
import su.sa1zer.bookparser.entity.Book;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;

import java.util.List;

public interface FilterBookRepository {

    List<Book> findAllByFilter(BookFilterRequest request);
}
