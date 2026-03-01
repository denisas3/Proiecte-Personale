package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Bilet;
import org.example.restaurante.domain.exceptions.ValidationException;

public class BiletValidator implements Validator<Bilet> {

    @Override
    public void validate(Bilet bilet) throws ValidationException {
        if (bilet == null) {
            throw new ValidationException("Item is null");
        }

        if (bilet.getUsername() == null || bilet.getUsername().trim().equals("")) {
            throw new ValidationException("Username is invalid");
        }

        if (bilet.getId_zbor() == null || bilet.getId_zbor() <= 0) {
            throw new ValidationException("Id_zbor is invalid");
        }

        if (bilet.getData_cumparare() == null) {
            throw new ValidationException("Data_cumparare is invalid");
        }
    }
}