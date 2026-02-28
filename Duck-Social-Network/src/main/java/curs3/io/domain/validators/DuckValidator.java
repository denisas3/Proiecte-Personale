package curs3.io.domain.validators;

import curs3.io.domain.Duck;
import curs3.io.domain.Persoana;

public class DuckValidator implements Validator<Duck> {

    @Override
    public void validate(Duck duck) throws ValidationException {
        new UtilizatorValidator().validate(duck);

        if (duck.getSpeed() < 0 || duck.getSpeed() > 100) {
            throw new ValidationException("Speed must be between 0 and 100.");
        }

        if (duck.getResistance() < 0 || duck.getResistance() > 100) {
            throw new ValidationException("Resistance must be between 0 and 100.");
        }

//        if (duck.getType() == null || duck.getType().isEmpty()) {
//            throw new ValidationException("Type of duck must not be empty.");
//        }
//
//        if (!duck.getType().trim().equalsIgnoreCase("swimming") &&
//                !duck.getType().trim().equalsIgnoreCase("flying") &&
//                !duck.getType().trim().equalsIgnoreCase("flying_and_swimming")) {
//            throw new ValidationException("Type of duck must be one of these: swimming, flying or flying_and_swimming");
//        }

    }
}
