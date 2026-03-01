package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.exceptions.ValidationException;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidationException {
        if (client == null) {
            throw new ValidationException("Item is null");
        }

        if (client.getUsername() == null || client.getUsername().trim().equals("")) {
            throw new ValidationException("Username is invalid");
        }

        if (client.getNume() == null || client.getNume().trim().equals("")) {
            throw new ValidationException("Nume is invalid");
        }
    }
}