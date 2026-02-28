package curs3.io.domain.validators;


import curs3.io.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {

    @Override
    public void validate(Utilizator entity) throws ValidationException {
//        if (entity.getId() == null || entity.getId() <= 0) {
//            throw new ValidationException("ID-ul utilizatorului trebuie sa fie pozitiv!");
//        }

        if (entity.getUsername() == null || entity.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username-ul nu poate fi gol!");
        }

        if (entity.getEmail() == null || entity.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email-ul nu poate fi gol!");
        }

        if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
            throw new ValidationException("Parola nu poate fi gol!");
        }
    }
}



