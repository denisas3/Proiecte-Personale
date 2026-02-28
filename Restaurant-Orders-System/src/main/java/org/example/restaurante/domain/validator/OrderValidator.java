package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Order;
import org.example.restaurante.domain.exceptions.ValidationException;

public class OrderValidator implements Validator<Order> {

    @Override
    public void validate(Order order) throws ValidationException {
        if (order == null) {
            throw new ValidationException("Order is null");
        }

        if (order.getTable() <= 0) {
            throw new ValidationException("Table must be a positive number");
        }

        if (order.getDate() == null) {
            throw new ValidationException("Date cannot be null");
        }

        if (order.getStatus() == null) {
            throw new ValidationException("Status cannot be null");
        }
    }
}