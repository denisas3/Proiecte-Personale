package org.example.restaurante.domain;

import java.time.LocalDate;

public class OferteSpeciale extends Entity<Integer>{
    private Integer id_hotel;
    private LocalDate data_inceput;
    private LocalDate data_final;
    private Integer procent;

    public OferteSpeciale(Integer id_hotel, LocalDate data_inceput, LocalDate data_final, Integer procent) {
        this.id_hotel = id_hotel;
        this.data_inceput = data_inceput;
        this.data_final = data_final;
        this.procent = procent;
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

    public Integer getProcent() {
        return procent;
    }
    public void setProcent(Integer procent) {
        this.procent = procent;
    }

}
