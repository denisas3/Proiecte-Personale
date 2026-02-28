package org.example.restaurante.domain;

import java.security.PublicKey;

public class Medic extends Entity<Integer>{
    private Integer id_sectie;
    private String nume;
    private Integer vechime;
    private Status rezident;

    public Medic(Integer id_sectie, String nume, Integer vechime, Status rezident){
        this.id_sectie = id_sectie;
        this.nume = nume;
        this.vechime = vechime;
        this.rezident = rezident;
    }

    public Integer getId_sectie() {
        return id_sectie;
    }
    public void setId_sectie(Integer id_sectie) {
        this.id_sectie = id_sectie;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVechime() {
        return vechime;
    }
    public void setVechime(Integer vechime) {
        this.vechime = vechime;
    }

    public Status getRezident() {
        return rezident;
    }
    public void setRezident(Status rezident) {
        this.rezident = rezident;
    }
}

