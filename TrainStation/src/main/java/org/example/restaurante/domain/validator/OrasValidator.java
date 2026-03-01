package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Oras;
import org.example.restaurante.domain.exceptions.ValidationException;

public class OrasValidator implements Validator<Oras>{
    @Override
    public void validate(Oras oras) throws ValidationException {
        if (oras == null) {
            throw new ValidationException("oras is null");
        }
        if(oras.getNume() == null || oras.getNume().trim().equals("")){
            throw new ValidationException("oras is invalid");
        }
    }
}
