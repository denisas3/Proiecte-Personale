package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.MenuItem;
import org.example.restaurante.domain.exceptions.ValidationException;

public class MenuItemValidator implements Validator<MenuItem>{

    @Override
    public void validate(MenuItem item) throws ValidationException {
        if(item == null){
            throw new ValidationException("Item is null");
        }
        if(item.getCategory() == null){
            throw new ValidationException("Category is null");
        }
        if(item.getCurrency() == null){
            throw new ValidationException("Currency is null");
        }
        if(item.getPrice() <= 0){
            throw new ValidationException("Price is negative");
        }
    }

}
