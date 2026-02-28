package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.exceptions.ValidationException;

public class ConsultatieValidator implements Validator<Consultatie> {

    @Override
    public void validate(Consultatie c) throws ValidationException {
        if (c == null) {
            throw new ValidationException("Consultatie must not be null.");
        }

        if (c.getId_medic() == null || c.getId_medic() <= 0) {
            throw new ValidationException("Invalid medic id.");
        }

        if (c.getCnp() == null || c.getCnp() <= 0) {
            throw new ValidationException("Invalid CNP.");
        }

        if (c.getNume() == null || c.getNume().isBlank()) {
            throw new ValidationException("Invalid patient name.");
        }

        if (c.getData() == null) {
            throw new ValidationException("Date must not be null.");
        }

        if (c.getOra() == null) {
            throw new ValidationException("Time must not be null.");
        }
    }
}