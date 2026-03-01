package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}