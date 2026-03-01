package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.User;
import org.example.restaurante.domain.exceptions.ValidationException;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User is null");
        }
        if(user.getUsername() == null || user.getUsername().trim().equals("")){
            throw new ValidationException("Data is invalid");
        }
        if(user.getBuget() == null || user.getBuget()==0){
            throw new ValidationException("Data is invalid");
        }
    }
}
