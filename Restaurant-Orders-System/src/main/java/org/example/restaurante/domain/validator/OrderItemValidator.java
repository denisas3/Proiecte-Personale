package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.OrderItems;
import org.example.restaurante.domain.exceptions.ValidationException;

public class OrderItemValidator implements Validator<OrderItems> {

    @Override
    public void validate(OrderItems orderItem) throws ValidationException {
        if (orderItem == null) {
            throw new ValidationException("OrderItem is null");
        }

        if (orderItem.getOrder_id() <= 0) {
            throw new ValidationException("order_id must be a positive integer");
        }

        if (orderItem.getMenu_item_id() <= 0) {
            throw new ValidationException("menu_item_id must be a positive integer");
        }
    }
}