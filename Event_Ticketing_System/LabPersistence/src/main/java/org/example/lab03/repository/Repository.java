package org.example.lab03.repository;

import org.example.lab03.domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();

    E save(E entity);

//    E delete(ID id);

    E update(E entity);

}
