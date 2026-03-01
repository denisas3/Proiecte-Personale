package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Tren;
import org.example.restaurante.domain.exceptions.ValidationException;

public class TrenValidator implements Validator<Tren> {

    @Override
    public void validate(Tren tren) throws ValidationException {
        if (tren == null) {
            throw new ValidationException("Item is null");
        }

        if (tren.getNume() == null || tren.getNume().trim().equals("")) {
            throw new ValidationException("Nume is invalid");
        }
    }
}