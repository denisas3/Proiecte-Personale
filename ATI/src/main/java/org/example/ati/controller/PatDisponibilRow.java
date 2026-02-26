package org.example.ati.controller;

public class PatDisponibilRow {
    private final String tip;
    private final Integer disponibile;

    public PatDisponibilRow(String tip, Integer disponibile) {
        this.tip = tip;
        this.disponibile = disponibile;
    }

    public String getTip() {
        return tip;
    }

    public Integer getDisponibile() {
        return disponibile;
    }
}
