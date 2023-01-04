package su.sa1zer.bookparser.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sa1zer.bookparser.entity.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, FilterBookRepository {

    List<Book> findAllByYear(LocalDate date);

    Optional<Book> findByName(String name);
}
