package org.example.ati.domain.validator;

import org.example.ati.domain.Pacient;
import org.example.ati.domain.exceptions.ValidationException;

public class PacientValidator implements Validator<Pacient> {

    @Override
    public void validate(Pacient p) throws ValidationException {
        if (p == null) {
            throw new ValidationException("Pacientul nu poate fi null.");
        }

        StringBuilder errors = new StringBuilder();

        if (p.getCnp() == null) {
            errors.append("CNP-ul nu poate fi null.\n");
        } else {
            String cnpStr = String.valueOf(p.getCnp());
            if (!cnpStr.matches("\\d+")) {
                errors.append("CNP-ul trebuie sa contina doar cifre.\n");
            } else if (cnpStr.length() != 13) {
                errors.append("CNP-ul trebuie sa aiba exact 13 cifre.\n");
            }
        }

        if (p.getVarsta() == null) {
            errors.append("Varsta nu poate fi null.\n");
        } else if (p.getVarsta() < 0 || p.getVarsta() > 14) {
            errors.append("Varsta trebuie sa fie intre 0 si 14.\n");
        }

        if (p.getPrematur() == null) {
            errors.append("Campul 'prematur' (Status) nu poate fi null.\n");
        }

        if (p.getDiagnostic_principal() == null || p.getDiagnostic_principal().trim().isEmpty()) {
            errors.append("Diagnosticul principal nu poate fi null/gol.\n");
        }

        if (p.getGravitate() == null) {
            errors.append("Gravitatea nu poate fi null.\n");
        } else if (p.getGravitate() < 1 || p.getGravitate() > 10) {
            errors.append("Gravitatea trebuie sa fie intre 1 si 10.\n");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString().trim());
        }
    }
}