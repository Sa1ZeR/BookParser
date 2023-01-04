package su.sa1zer.bookparser.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sa1zer.bookparser.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
}
