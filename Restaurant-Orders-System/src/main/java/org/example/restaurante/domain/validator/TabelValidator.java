package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Tabel;
import org.example.restaurante.domain.exceptions.ValidationException;

public class TabelValidator implements Validator<Tabel> {

    @Override
    public void validate(Tabel tabel) throws ValidationException {
        if (tabel == null) {
            throw new ValidationException("Tabel is null");
        }

        if (tabel.getId() == null) {
            throw new ValidationException("Tabel ID cannot be null");
        }

        if (tabel.getId() <= 0) {
            throw new ValidationException("Tabel ID must be positive");
        }
    }
}