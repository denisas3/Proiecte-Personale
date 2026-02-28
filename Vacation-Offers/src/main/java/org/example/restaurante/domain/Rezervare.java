package org.example.restaurante.domain;

import java.time.LocalDate;

public class Rezervare extends Entity<Integer> {
    private Integer id_client;
    private Integer id_hotel;
    private LocalDate data_inceput;
    private LocalDate data_final;

    public Rezervare(Integer id_client, Integer id_hotel, LocalDate data_inceput, LocalDate data_final) {
        this.id_client = id_client;
        this.id_hotel = id_hotel;
        this.data_inceput = data_inceput;
        this.data_final = data_final;
    }

    public Integer getId_client() {
        return id_client;
    }
    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Integer getId_hotel() {
        return id_hotel;
    }
    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public LocalDate getData_inceput() {
        return data_inceput;
    }
    public void setData_inceput(LocalDate data_inceput) {
        this.data_inceput = data_inceput;
    }

    public LocalDate getData_final() {
        return data_final;
    }
    public void setData_final(LocalDate data_final) {
        this.data_final = data_final;
    }


}
