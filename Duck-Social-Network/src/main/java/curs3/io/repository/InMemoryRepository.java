package curs3.io.repository;



import curs3.io.domain.Card;
import curs3.io.domain.Duck;
import curs3.io.domain.Entity;
import curs3.io.domain.Utilizator;
import curs3.io.domain.validators.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {
        private Map<ID, E> entities = new HashMap<>();
        Validator<E> validator;

        public InMemoryRepository(Validator<E> validator) {
            this.validator = validator;
            //entities = new HashMap<>();
        }


    @Override
    public E findOne(ID id) {
        if(id == null)
            throw new IllegalArgumentException("Id can't be null!");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity can't be null!");
        validator.validate(entity);

        if(findOne(entity.getId()) != null)
            return entity;

        entities.put(entity.getId(), entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(id == null) {
            throw new IllegalArgumentException("Id can't be null!");
        }
        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity can't be null!");
        if(findOne(entity.getId()) == null)
            return entity;

        validator.validate(entity);
        entities.put(entity.getId(), entity);
        return null;
    }

    public void addFriendRepo(Utilizator u1, Utilizator u2) throws Exception {
        if(!u1.isFriend(u2))
        {
            u1.addFriend(u2);
            u2.addFriend(u1);
        }
        else
            throw new Exception("Utilizatorii sunt deja prieteni.\n");
    }

    public void removeUserAndRelations(Utilizator u) {
        for (Utilizator other : u.getFriends()) {
            other.removeFriend(u);
        }
        entities.remove(u.getId());
    }

    public void removeFriendRepo(Utilizator u1, Utilizator u2) throws Exception {
            if(u1.isFriend(u2))
            {
                u1.removeFriend(u2);
                u2.removeFriend(u1);
            }
            else
                throw new Exception("Utilizatorii nu sunt prieteni.\n");
    }

}
