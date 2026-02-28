package org.example.restaurante.domain;

public class Sectie extends Entity<Integer>{
    private String nume;
    private Integer pret;
    private Integer durata;

    public Sectie(String nume, Integer pret, Integer durata) {
        this.nume = nume;
        this.pret = pret;
        this.durata = durata;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getPret() {
        return pret;
    }
    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public Integer getDurata() {
        return durata;
    }
    public void setDurata(Integer durata) {
        this.durata = durata;
    }
}
