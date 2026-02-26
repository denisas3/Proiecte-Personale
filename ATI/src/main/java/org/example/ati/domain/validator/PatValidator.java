package org.example.ati.domain.validator;

import org.example.ati.domain.Pat;
import org.example.ati.domain.exceptions.ValidationException;

public class PatValidator implements Validator<Pat> {

    @Override
    public void validate(Pat pat) throws ValidationException {
        if (pat == null) {
            throw new ValidationException("Pat-ul nu poate fi null.");
        }

        StringBuilder errors = new StringBuilder();

        if (pat.getTip() == null || pat.getTip().trim().isEmpty()) {
            errors.append("Tipul patului nu poate fi null/gol.\n");
        }

        if (pat.getVentilatie() == null) {
            errors.append("Ventilatia (Status) nu poate fi null.\n");
        }

        if (pat.getId_pacient() != null && pat.getId_pacient() <= 0) {
            errors.append("Id-ul pacientului trebuie sa fie > 0 (sau null daca patul e liber).\n");
        }

        if (pat.getNr_pat_disponibile() == null) {
            errors.append("Nr. de paturi disponibile nu poate fi null.\n");
        } else if (pat.getNr_pat_disponibile() < 0) {
            errors.append("Nr. de paturi disponibile nu poate fi negativ.\n");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString().trim());
        }
    }
}