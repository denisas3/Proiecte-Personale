package curs3.io.service;

import curs3.io.domain.Duck;
import curs3.io.domain.DuckType;
import curs3.io.domain.Utilizator;
import curs3.io.domain.validators.DuckValidator;
import curs3.io.repository.DuckRepository;
import curs3.io.repository.RepositoryBD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDuck {
    private final DuckRepository duckRepo;
    private final RepositoryBD<Long, Utilizator> userRepo;
    private final DuckValidator duckValidator;


    public ServiceDuck(DuckRepository duckRepo, RepositoryBD<Long, Utilizator> userRepo, DuckValidator duckValidator) {
        this.duckRepo = duckRepo;
        this.userRepo = userRepo;
        this.duckValidator = duckValidator;
    }

    public void addDuck(Duck duck){
        try{
            duckValidator.validate(duck);

            String hashed = ServiceUser.PasswordUtils.hashPassword(duck.getPassword());
            duck.setPassword(hashed);

            Utilizator user = new Utilizator(
                    duck.getUsername(),
                    duck.getEmail(),
                    duck.getPassword()
            );

            userRepo.save(user);

            duck.setId(user.getId());

            duckRepo.save(duck);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void removeDuck(Long duckid){
        try{

            Optional<Duck> duckOptional = duckRepo.findById(duckid);

            if (duckOptional.isEmpty()) {
                throw new RuntimeException("Duck does not exist!\n");
            }

            Optional<Duck> result = duckRepo.delete(duckid);

            if (result.isEmpty()) {
                throw new RuntimeException("Could not delete duck!");
            }
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateDuck(Duck duck) {
        try {
            duckValidator.validate(duck);

            Optional<Duck> updatedDuck = duckRepo.update(duck);

            if (updatedDuck.isEmpty()) {
                throw new RuntimeException("User does not exist!\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Duck findById(Long duckID) {
        return duckRepo.findById(duckID)
                .orElseThrow(() -> new RuntimeException("Duck not found!"));
    }

    public Iterable<Duck> findAll() {
        return duckRepo.getAll();
    }

    public List<Duck> getAllDucks() {
        List<Duck> lista = new ArrayList<>();
        for (Duck d : duckRepo.getAll()) {
            lista.add(d);
        }
        return lista;
    }

//    public List<Duck> getPage(int pageNumber, int pageSize) {
//        int offset = pageNumber * pageSize;
//        return duckRepo.findPage(offset, pageSize);
//    }

    public int getTotalDucks() {
        return duckRepo.countDucks();
    }

    public List<Duck> getPageFiltered(DuckType type, int page, int size) {
        return duckRepo.getPageFiltered(type, page, size);
    }

    public List<Duck> getPage(int page, int size) {
        try {
            return duckRepo.getPage(null, page, size);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while loading page", e);
        }
    }

    public int countFiltered(DuckType type) {
        try {
            return duckRepo.countFiltered(type);
        } catch (Exception e) {
            throw new RuntimeException("Could not count filtered ducks", e);
        }
    }






}
