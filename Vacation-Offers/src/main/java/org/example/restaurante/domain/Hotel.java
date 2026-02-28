package org.example.restaurante.domain;

public class Hotel extends Entity<Integer>{
    private Integer id_locatie;
    private String hotel_name;
    private Integer nr_camere;
    private Double pret_per_noapte;
    private Status tip;

    public Hotel(Integer id_locatie, String hotel_name, Integer nr_camera, Double pret_per_noapte, Status tip) {
        this.id_locatie = id_locatie;
        this.hotel_name = hotel_name;
        this.nr_camere = nr_camera;
        this.pret_per_noapte = pret_per_noapte;
        this.tip = tip;
    }

    public Integer getId_locatie() {
        return id_locatie;
    }
    public void setId_locatie(Integer id_locatie) {
        this.id_locatie = id_locatie;
    }

    public String getHotel_name() {
        return hotel_name;
    }
    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public Integer getNr_camera() {
        return nr_camere;
    }
    public void setNr_camera(Integer nr_camera) {
        this.nr_camere = nr_camera;
    }

    public Double getPret_per_noapte() {
        return pret_per_noapte;
    }
    public void setPret_per_noapte(Double pret_per_noapte) {
        this.pret_per_noapte = pret_per_noapte;
    }

    public Status getTip() {
        return tip;
    }
    public void setTip(Status tip) {
        this.tip = tip;
    }

}

