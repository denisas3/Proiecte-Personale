package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.exceptions.ValidationException;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidationException {
        if (client == null) {
            throw new ValidationException("Client is null");
        }

        if (client.getNume() == null || client.getNume().trim().isEmpty()) {
            throw new ValidationException("Nume invalid");
        }

        if (client.getGrad_fidelitate() == null || client.getGrad_fidelitate() < 0) {
            throw new ValidationException("Grad fidelitate invalid");
        }

        if (client.getVarsta() == null || client.getVarsta() <= 0) {
            throw new ValidationException("Varsta invalida");
        }

        if (client.getPasiune() == null) {
            throw new ValidationException("Pasiune este null");
        }
    }
}