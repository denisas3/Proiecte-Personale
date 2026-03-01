package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Coins;
import org.example.restaurante.domain.exceptions.ValidationException;

public class CoinValidator implements Validator<Coins>{

    @Override
    public void validate(Coins coin) throws ValidationException {
        if (coin == null) {
            throw new ValidationException("Item is null");
        }
        if(coin.getNume() == null || coin.getNume().trim().equals("")){
            throw new ValidationException("Nume is invalid");
        }
        if(coin.getPret() == null || coin.getPret()<=0){
            throw new ValidationException("Pret is invalid");
        }
    }

}
