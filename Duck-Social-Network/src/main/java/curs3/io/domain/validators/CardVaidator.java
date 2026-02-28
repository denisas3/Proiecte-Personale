package curs3.io.domain.validators;

import curs3.io.domain.Card;
import curs3.io.domain.Duck;
import curs3.io.domain.Utilizator;

public class CardVaidator implements Validator<Card<Duck>>{
    @Override
    public void validate(Card<Duck> card) throws ValidationException {
        if (!card.getNumeCard().trim().equalsIgnoreCase("swimming") &&
                !card.getNumeCard().trim().equalsIgnoreCase("flying"))
            throw new ValidationException("Type of card must be one of these: swimming or flying");
        }
}
