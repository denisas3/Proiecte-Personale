package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.exceptions.ValidationException;

public class MasinaValidator implements Validator<Masina> {

    @Override
    public void validate(Masina masina) throws ValidationException {
        if (masina == null) {
            throw new ValidationException("Masina is null");
        }

        if (masina.getDenumire_masina() == null || masina.getDenumire_masina().trim().isEmpty()) {
            throw new ValidationException("Denumire masina invalida");
        }

        if (masina.getDescriere_masina() == null || masina.getDescriere_masina().trim().isEmpty()) {
            throw new ValidationException("Descriere masina invalida");
        }

        if (masina.getPret_de_baza() == null || masina.getPret_de_baza() <= 0) {
            throw new ValidationException("Pret de baza invalid");
        }

        if (masina.getStatur_curent() == null) {
            throw new ValidationException("Status curent este null");
        }
    }
}