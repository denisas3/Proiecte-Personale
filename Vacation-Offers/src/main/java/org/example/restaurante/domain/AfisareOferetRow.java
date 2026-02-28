package org.example.restaurante.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;


/// DTO PT OBIECTUL CE TREBUIE AFISAT IN TABEL
public class AfisareOferetRow {
    private final String nume_hotel;
    private final String nume_locatie;
    private final LocalDate data_inceput;
    private final LocalDate data_final;

    public AfisareOferetRow(String nume_hotel, String nume_locatie, LocalDate data_inceput, LocalDate data_final) {
        this.nume_hotel = nume_hotel;
        this.nume_locatie = nume_locatie;
        this.data_inceput = data_inceput;
        this.data_final = data_final;
    }

    public String getNume_hotel() {
        return nume_hotel;
    }
    public String getNume_locatie() {
        return nume_locatie;
    }
    public LocalDate getData_inceput() {
        return data_inceput;
    }
    public LocalDate getData_final() {
        return data_final;
    }
}
