package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Detaliu;
import org.example.restaurante.domain.exceptions.ValidationException;

public class DetaliuValidator implements Validator<Detaliu> {

    @Override
    public void validate(Detaliu item) throws ValidationException {
        if (item == null) {
            throw new ValidationException("Detaliu is null");
        }

        if (item.getId_masina() == null || item.getId_masina() <= 0) {
            throw new ValidationException("Invalid masina id");
        }

        if (item.getDetaliu() == null || item.getDetaliu().trim().isEmpty()) {
            throw new ValidationException("Detaliu field is empty");
        }

        if (item.getComentariu() == null) {
            throw new ValidationException("Comentariu cannot be null");
        }
    }
}