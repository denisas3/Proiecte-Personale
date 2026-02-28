package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Sectie;
import org.example.restaurante.domain.exceptions.ValidationException;

public class SectieValidator implements Validator<Sectie> {

    @Override
    public void validate(Sectie sectie) throws ValidationException {
        if (sectie == null) {
            throw new ValidationException("Sectie must not be null.");
        }

        if (sectie.getNume() == null || sectie.getNume().isBlank()) {
            throw new ValidationException("Invalid section name.");
        }

        if (sectie.getPret() == null || sectie.getPret() <= 0) {
            throw new ValidationException("Invalid price.");
        }

        if (sectie.getDurata() == null || sectie.getDurata() <= 0) {
            throw new ValidationException("Invalid duration.");
        }
    }
}