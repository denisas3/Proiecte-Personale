package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Zbor;
import org.example.restaurante.domain.exceptions.ValidationException;

public class ZborValidator implements Validator<Zbor> {

    @Override
    public void validate(Zbor zbor) throws ValidationException {
        if (zbor == null) {
            throw new ValidationException("Item is null");
        }

        if (zbor.getDe_unde() == null || zbor.getDe_unde().trim().equals("")) {
            throw new ValidationException("De_unde is invalid");
        }

        if (zbor.getPana_unde() == null || zbor.getPana_unde().trim().equals("")) {
            throw new ValidationException("Pana_unde is invalid");
        }

        if (zbor.getDecolare() == null) {
            throw new ValidationException("Decolare is invalid");
        }

        if (zbor.getAterizare() == null) {
            throw new ValidationException("Aterizare is invalid");
        }

        if (zbor.getLoc() == null || zbor.getLoc() <= 0) {
            throw new ValidationException("Loc is invalid");
        }
    }
}