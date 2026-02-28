package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.OferteSpeciale;
import org.example.restaurante.domain.exceptions.ValidationException;

public class OferteSpecialeValidator implements Validator<OferteSpeciale> {

    @Override
    public void validate(OferteSpeciale oferta) throws ValidationException {
        if (oferta == null) {
            throw new ValidationException("Oferta is null");
        }

        if (oferta.getId_hotel() == null || oferta.getId_hotel() <= 0) {
            throw new ValidationException("Id hotel invalid");
        }

        if (oferta.getData_inceput() == null) {
            throw new ValidationException("Data inceput is null");
        }

        if (oferta.getData_final() == null) {
            throw new ValidationException("Data final is null");
        }

        if (oferta.getData_final().isBefore(oferta.getData_inceput())) {
            throw new ValidationException("Data final nu poate fi inainte de data inceput");
        }

        if (oferta.getProcent() == null || oferta.getProcent() < 0 || oferta.getProcent() > 100) {
            throw new ValidationException("Procent invalid (0-100)");
        }
    }
}