package curs3.io.repository;



import curs3.io.domain.Utilizator;
import curs3.io.domain.validators.Validator;

import java.util.List;

public class UtilizatorFileRepository extends AbstractFileRepository<Long, Utilizator> {

    public UtilizatorFileRepository(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        return null;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return "";
    }
}