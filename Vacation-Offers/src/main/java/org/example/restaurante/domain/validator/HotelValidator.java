package org.example.restaurante.domain.validator;

import org.example.restaurante.domain.Hotel;
import org.example.restaurante.domain.exceptions.ValidationException;

public class HotelValidator implements Validator<Hotel> {

    @Override
    public void validate(Hotel hotel) throws ValidationException {
        if (hotel == null) {
            throw new ValidationException("Hotel is null");
        }

        if (hotel.getId_locatie() == null || hotel.getId_locatie() <= 0) {
            throw new ValidationException("Id locatie invalid");
        }

        if (hotel.getHotel_name() == null || hotel.getHotel_name().trim().isEmpty()) {
            throw new ValidationException("Hotel name invalid");
        }

        if (hotel.getNr_camera() == null || hotel.getNr_camera() <= 0) {
            throw new ValidationException("Numar camere invalid");
        }

        if (hotel.getPret_per_noapte() == null || hotel.getPret_per_noapte() <= 0) {
            throw new ValidationException("Pret per noapte invalid");
        }

        if (hotel.getTip() == null) {
            throw new ValidationException("Status tip este null");
        }
    }
}