package su.sa1zer.bookparser.repostory;

import su.sa1zer.bookparser.entity.Book;
import su.sa1zer.bookparser.payload.request.BookFilterRequest;

import java.util.List;

public interface FilterBookRepository {

    List<Book> findAllByFilter(BookFilterRequest request);
}
