package curs3.io.service;

import curs3.io.domain.Duck;
import curs3.io.domain.Persoana;
import curs3.io.domain.Utilizator;
import curs3.io.domain.validators.PersoanaValidator;
import curs3.io.repository.RepositoryBD;

import java.util.Optional;

public class ServicePersoana {
    private final RepositoryBD<Long, Persoana> persoanaRepo;
    private final RepositoryBD<Long, Utilizator> userRepo;
    private final PersoanaValidator persoanaValidator;

    public ServicePersoana(RepositoryBD<Long, Persoana> persoanaRepo, RepositoryBD<Long, Utilizator> userRepo ,PersoanaValidator persoanaValidator) {
        this.persoanaRepo = persoanaRepo;
        this.userRepo = userRepo;
        this.persoanaValidator = persoanaValidator;
    }

    public void addPersoana(Persoana persoana){
        try{
            persoanaValidator.validate(persoana);
            String hashed = ServiceUser.PasswordUtils.hashPassword(persoana.getPassword());
            persoana.setPassword(hashed);

            if (userRepo.findByUsername(persoana.getUsername()).isPresent())
                throw new RuntimeException("Username already taken!");

            if (userRepo.findByEmail(persoana.getEmail()).isPresent())
                throw new RuntimeException("Email already used!");

            Utilizator user = new Utilizator(
                    persoana.getUsername(),
                    persoana.getEmail(),
                    persoana.getPassword()
            );

            userRepo.save(user);

            persoana.setUserID(user.getId());
            persoanaRepo.save(persoana);

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void removePersoana(Long persoanaId){
        try{
            Optional<Persoana> persoana = persoanaRepo.findById(persoanaId);

            if (persoana.isEmpty())
                throw new RuntimeException("Persoana does not exist!");

            Long userID = persoana.get().getUserID();

            persoanaRepo.delete(persoanaId);
            userRepo.delete(userID);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updatePersoana(Persoana persoana) {
        try {
            persoanaValidator.validate(persoana);

            Optional<Persoana> updatedPersoana = persoanaRepo.update(persoana);

            if (updatedPersoana.isEmpty()) {
                throw new RuntimeException("User does not exist!\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<Persoana> findAll() {
        return persoanaRepo.getAll();
    }
}
