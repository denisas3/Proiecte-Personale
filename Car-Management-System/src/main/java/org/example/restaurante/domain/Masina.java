package org.example.restaurante.domain;

public class Masina extends Entity<Integer>{
    private String denumire_masina;
    private String descriere_masina;
    private Double pret_de_baza;
    private Status statur_curent;

    public Masina(String denumire_masina,  String descriere_masina, Double pret_de_baza, Status statur_curent) {
        this.denumire_masina = denumire_masina;
        this.descriere_masina = descriere_masina;
        this.pret_de_baza = pret_de_baza;
        this.statur_curent = statur_curent;
    }

    public String getDenumire_masina() {
        return denumire_masina;
    }
    public void setDenumire_masina(String denumire_masina) {
        this.denumire_masina = denumire_masina;
    }

    public String getDescriere_masina() {
        return descriere_masina;
    }
    public void setDescriere_masina(String descriere_masina) {
        this.descriere_masina = descriere_masina;
    }

    public Double getPret_de_baza() {
        return pret_de_baza;
    }
    public void setPret_de_baza(Double pret_de_baza) {
        this.pret_de_baza = pret_de_baza;
    }

    public Status getStatur_curent() {
        return statur_curent;
    }
    public void setStatur_curent(Status statur_curent) {
        this.statur_curent = statur_curent;
    }
}

