package curs3.io.domain.validators;

import curs3.io.domain.Persoana;
import curs3.io.domain.Utilizator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class PersoanaValidator implements Validator<Persoana>{
    private static final DateTimeFormatter BDAY_FMT =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    @Override
    public void validate(Persoana persoana) throws ValidationException {
        new UtilizatorValidator().validate(persoana);

        if (persoana.getJobTitle()==null || persoana.getJobTitle().trim().isEmpty()){
            throw new ValidationException("Job is required");
        }

        if (persoana.getBirthDate() == null) {
            throw new ValidationException("Birthday is required");
        }
        if (persoana.getBirthDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday can't be in the future");
        }

//        try {
//            return LocalDate.parse(persoana.getBirthDate().toString(), BDAY_FMT); // STRICT: respinge 2000-233-234, 2025-02-30 etc.
//        } catch (DateTimeParseException e) {
//            throw new ValidationException("Birthday should be in the format yyyy-MM-dd and be a real date (e.g., 2000-12-24).");
//        }

        if (persoana.getEmpathyLevel() == null) {
            throw new ValidationException("EmpathyLevel is required");
        }
        if (persoana.getEmpathyLevel() < 0 || persoana.getEmpathyLevel() > 100) {
            throw new ValidationException("EmpathyLevel must be between 0 and 100.");
 }
}
}
