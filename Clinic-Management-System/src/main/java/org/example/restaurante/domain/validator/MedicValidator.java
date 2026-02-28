package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Medic;
import org.example.restaurante.domain.exceptions.ValidationException;

public class MedicValidator implements Validator<Medic> {

    @Override
    public void validate(Medic m) throws ValidationException {
        if (m == null) {
            throw new ValidationException("Medic must not be null.");
        }

        if (m.getId_sectie() == null || m.getId_sectie() <= 0) {
            throw new ValidationException("Invalid section id.");
        }

        if (m.getNume() == null || m.getNume().isBlank()) {
            throw new ValidationException("Invalid name.");
        }

        if (m.getVechime() == null || m.getVechime() < 0) {
            throw new ValidationException("Invalid experience (vechime).");
        }

        if (m.getRezident() == null) {
            throw new ValidationException("Status must not be null.");
        }
    }
}