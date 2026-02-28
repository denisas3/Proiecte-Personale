package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Locatie;
import org.example.restaurante.domain.exceptions.ValidationException;

public class LocatieValidator implements Validator<Locatie> {

    @Override
    public void validate(Locatie locatie) throws ValidationException {
        if (locatie == null) {
            throw new ValidationException("Locatie is null");
        }

        if (locatie.getNume() == null || locatie.getNume().trim().isEmpty()) {
            throw new ValidationException("Nume locatie invalid");
        }
    }
}