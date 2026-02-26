package org.example.ati.domain;

public class Pacient extends Entity<Integer>{
    private Integer cnp;
    private Integer varsta;
    private Status prematur;
    private String diagnostic_principal;
    private Integer gravitate;

    public Pacient(Integer cnp, Integer varsta, Status prematur, String diagnostic_principal, Integer gravitate) {
        this.cnp = cnp;
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnostic_principal = diagnostic_principal;
        this.gravitate = gravitate;
    }

    public Integer getCnp() {
        return cnp;
    }
    public void setCnp(Integer cnp) {
        this.cnp = cnp;
    }

    public Integer getVarsta() {
        return varsta;
    }
    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    public Status getPrematur() {
        return prematur;
    }
    public void setPrematur(Status prematur) {
        this.prematur = prematur;
    }

    public String getDiagnostic_principal() {
        return diagnostic_principal;
    }
    public void setDiagnostic_principal(String diagnostic_principal) {this.diagnostic_principal = diagnostic_principal;}

    public Integer getGravitate() {
        return gravitate;
    }
    public void setGravitate(Integer gravitate) {
        this.gravitate = gravitate;
    }

}
