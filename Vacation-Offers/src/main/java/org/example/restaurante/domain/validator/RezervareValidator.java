package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Rezervare;
import org.example.restaurante.domain.exceptions.ValidationException;

public class RezervareValidator implements Validator<Rezervare> {

    @Override
    public void validate(Rezervare rezervare) throws ValidationException {
        if (rezervare == null) {
            throw new ValidationException("Rezervare is null");
        }

        if (rezervare.getId_client() == null || rezervare.getId_client() <= 0) {
            throw new ValidationException("Id client invalid");
        }

        if (rezervare.getId_hotel() == null || rezervare.getId_hotel() <= 0) {
            throw new ValidationException("Id hotel invalid");
        }

        if (rezervare.getData_inceput() == null) {
            throw new ValidationException("Data inceput is null");
        }

        if (rezervare.getData_final() == null) {
            throw new ValidationException("Data final is null");
        }

        if (rezervare.getData_final().isBefore(rezervare.getData_inceput())) {
            throw new ValidationException("Data final nu poate fi inainte de data inceput");
        }
    }
}