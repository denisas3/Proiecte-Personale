package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Transaction;
import org.example.restaurante.domain.exceptions.ValidationException;

public class TransactionValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction == null) {
            throw new ValidationException("Item is null");
        }

        if (transaction.getUserId() == null || transaction.getUserId() <= 0) {
            throw new ValidationException("UserId is invalid");
        }

        if (transaction.getCoinId() == null || transaction.getCoinId() <= 0) {
            throw new ValidationException("CoinId is invalid");
        }

        if (transaction.getType() == null) {
            throw new ValidationException("Type is invalid");
        }

        if (transaction.getPrice() == null || transaction.getPrice() <= 0) {
            throw new ValidationException("Price is invalid");
        }

        if (transaction.getTimestamp() == null) {
            throw new ValidationException("Timestamp is invalid");
        }
    }
}