package su.sa1zer.bookparser.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sa1zer.bookparser.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
