package org.example.ati.domain.validator;

import org.example.ati.domain.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}