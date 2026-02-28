package org.example.restaurante.domain;

public class Client extends Entity<Integer>{
    private String nume;
    private Integer grad_fidelitate;
    private Integer varsta;
    private Pasiuni pasiune;

    public Client(String nume, Integer grad_fidelitate, Integer varsta, Pasiuni pasiune) {
        this.nume = nume;
        this.grad_fidelitate = grad_fidelitate;
        this.varsta = varsta;
        this.pasiune = pasiune;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getGrad_fidelitate() {
        return grad_fidelitate;
    }
    public void setGrad_fidelitate(Integer grad_fidelitate) {
        this.grad_fidelitate = grad_fidelitate;
    }

    public Integer getVarsta() {
        return varsta;
    }
    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    public Pasiuni getPasiune() {
        return pasiune;
    }
    public void setPasiune(Pasiuni pasiune) {
        this.pasiune = pasiune;
    }
}
