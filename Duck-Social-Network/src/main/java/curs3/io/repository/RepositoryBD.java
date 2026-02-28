package curs3.io.repository;

import curs3.io.domain.Duck;
import curs3.io.domain.Entity;

import java.util.List;
import java.util.Optional;

public interface RepositoryBD<ID, E extends Entity<ID>> {
    Optional<E> findById(ID id);

    Optional<E> findByUsername(String username);

    Optional<E> findByEmail(String email);

    Iterable<E> getAll();

    Optional<E> save(E e);

    Optional<E> update(E e);

    Optional<E> delete(ID id);

    Page<E> findAll(RepositoryPaging paging);

    List<E> findPage(int offset, int pageSize);

    int countDucks();
}
