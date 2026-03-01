package org.example.restaurante.domain;

public class Oras extends Entity<Integer>{
    private String nume;

    public Oras(String nume) {
        this.nume = nume;

    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
}
