package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.User;
import org.example.restaurante.domain.exceptions.ValidationException;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User is null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is invalid");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is invalid");
        }
    }
}