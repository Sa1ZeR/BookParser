package su.sa1zer.bookparser.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import su.sa1zer.bookparser.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
}
