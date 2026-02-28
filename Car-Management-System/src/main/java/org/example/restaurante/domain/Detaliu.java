package org.example.restaurante.domain;

public class Detaliu extends Entity<Integer>{
    private Integer id_masina;
    private String detaliu;
    private String comentariu;

    public Detaliu(Integer id_masina, String detaliu, String comentariu) {
        this.id_masina = id_masina;
        this.detaliu = detaliu;
        this.comentariu = comentariu;
    }

    public Integer getId_masina() {
        return id_masina;
    }
    public void setId_masina(Integer id_masina) {
        this.id_masina = id_masina;
    }

    public String getDetaliu() {
        return detaliu;
    }
    public void setDetaliu(String detaliu) {
        this.detaliu = detaliu;
    }

    public String getComentariu() {
        return comentariu;
    }
    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }
}
