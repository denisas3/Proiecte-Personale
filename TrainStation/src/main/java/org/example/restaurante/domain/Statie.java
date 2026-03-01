package org.example.restaurante.domain;

public class Statie extends Entity<Integer>{
    private Integer id_tren;
    private Integer id_oras_plecare;
    private Integer id_oras_sosier;

    public Statie(Integer id_tren, Integer id_oras_plecare, Integer id_oras_sosier){
        this.id_tren = id_tren;
        this.id_oras_plecare = id_oras_plecare;
        this.id_oras_sosier = id_oras_sosier;
    }

    public Integer getId_tren() {
        return id_tren;
    }
    public void setId_tren(Integer id_tren) {
        this.id_tren = id_tren;
    }

    public Integer getId_oras_plecare() {
        return id_oras_plecare;
    }
    public void setId_oras_plecare(Integer id_oras_plecare) {
        this.id_oras_plecare = id_oras_plecare;
    }

    public Integer getId_oras_sosier() {
        return id_oras_sosier;
    }
    public void setId_oras_sosier(Integer id_oras_sosier) {
        this.id_oras_sosier = id_oras_sosier;
    }
}

