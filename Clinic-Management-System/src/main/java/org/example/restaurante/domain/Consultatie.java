package org.example.restaurante.domain;

import javafx.scene.control.TableColumn;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;


public class Consultatie extends Entity<Integer>{
    private Integer id_medic;
    private Integer cnp;
    private String nume;
    private LocalDate data;
    private LocalTime ora;

    public Consultatie(Integer id_medic, Integer cnp, String nume, LocalDate data, LocalTime ora){
        this.id_medic = id_medic;
        this.cnp = cnp;
        this.nume = nume;
        this.data = data;
        this.ora = ora;
    }

    public Integer getId_medic() {
        return id_medic;
    }
    public void setId_medic(Integer id_medic) {
        this.id_medic = id_medic;
    }

    public Integer getCnp() {
        return cnp;
    }
    public void setCnp(Integer cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
    }
    public void setOra(LocalTime ora) {
        this.ora = ora;
    }
}
