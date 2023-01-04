package su.sa1zer.bookparser.service;

import java.util.List;

/**
 *
 * @param <O> - Base Entity Object
 * @param <K> - Id type of entity
 */
public interface CrudService<O, K> {

    O create(O dto);

    O update(Long id, O dto);

    void delete(K id);

    O findById(K id);

    List<O> findAll();
}
