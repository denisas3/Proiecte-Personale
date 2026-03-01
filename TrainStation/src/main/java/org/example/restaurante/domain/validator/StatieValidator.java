package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Statie;
import org.example.restaurante.domain.exceptions.ValidationException;

public class StatieValidator implements Validator<Statie> {

    @Override
    public void validate(Statie statie) throws ValidationException {
        if (statie == null) {
            throw new ValidationException("Item is null");
        }

        if (statie.getId_tren() == null || statie.getId_tren() <= 0) {
            throw new ValidationException("Id_tren is invalid");
        }

        if (statie.getId_oras_plecare() == null || statie.getId_oras_plecare() <= 0) {
            throw new ValidationException("Id_oras_plecare is invalid");
        }

        if (statie.getId_oras_sosier() == null || statie.getId_oras_sosier() <= 0) {
            throw new ValidationException("Id_oras_sosier is invalid");
        }
    }
}