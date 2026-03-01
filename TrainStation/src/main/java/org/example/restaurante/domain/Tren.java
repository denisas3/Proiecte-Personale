package org.example.restaurante.domain;

public class Tren extends Entity<Integer>{
    private String nume;

    public Tren(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
}
