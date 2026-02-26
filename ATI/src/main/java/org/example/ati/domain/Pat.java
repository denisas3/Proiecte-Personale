package org.example.ati.domain;

public class Pat extends Entity<Integer>{
    private String tip;
    private Status ventilatie;
    private Integer id_pacient;
    private Integer nr_pat_disponibile;

    public Pat(String tip, Status ventilatie, Integer id_pacient, Integer nr_pat_disponibile) {
        this.tip = tip;
        this.ventilatie = ventilatie;
        this.id_pacient = id_pacient;
        this.nr_pat_disponibile = nr_pat_disponibile;
    }

    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }

    public Status getVentilatie() {
        return ventilatie;
    }
    public void setVentilatie(Status ventilatie) {
        this.ventilatie = ventilatie;
    }

    public Integer getId_pacient() {
        return id_pacient;
    }
    public void setId_pacient(Integer id_pacient) {
        this.id_pacient = id_pacient;
    }

    public Integer getNr_pat_disponibile() {
        return nr_pat_disponibile;
    }
    public void setNr_pat_disponibile(Integer nr_pat_disponibile) {
        this.nr_pat_disponibile = nr_pat_disponibile;
    }

}

