package org.example.restaurante.domain;

public class Locatie extends Entity<Integer>{
    private String nume;

    public Locatie(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
}
