package org.example.restaurante.domain;

public class Coins extends Entity<Integer>{
    private String nume;
    private Float pret;

    public Coins(String nume,Float pret) {
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public Float getPret() {
        return pret;
    }
    public void setPret(Float pret) {
        this.pret = pret;
    }
}

