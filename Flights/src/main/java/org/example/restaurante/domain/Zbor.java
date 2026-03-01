package org.example.restaurante.domain;

import java.time.LocalDateTime;

public class Zbor extends Entity<Integer>{
    private String de_unde;
    private String pana_unde;
    private LocalDateTime decolare;
    private LocalDateTime aterizare;
    /// SUNT LOCURILE CARE MAI SUNT DISPONIBILE PT ACEL ZBOR
    private Integer loc;

    public Zbor(String de_unde, String pana_unde, LocalDateTime decolare, LocalDateTime aterizare, Integer loc){
        this.de_unde = de_unde;
        this.pana_unde = pana_unde;
        this.decolare = decolare;
        this.aterizare = aterizare;
        this.loc = loc;
    }

    public String getDe_unde(){
        return de_unde;
    }
    public String getPana_unde(){
        return pana_unde;
    }
    public LocalDateTime getDecolare(){
        return decolare;
    }
    public LocalDateTime getAterizare(){
        return aterizare;
    }
    public Integer getLoc(){
        return loc;
    }
    public void setDe_unde(String de_unde){
        this.de_unde = de_unde;
    }
    public void setPana_unde(String pana_unde){
        this.pana_unde = pana_unde;
    }
    public void setDecolare(LocalDateTime decolare){
        this.decolare = decolare;
    }
    public void setAterizare(LocalDateTime aterizare){
        this.aterizare = aterizare;
    }
    public void setLoc(Integer loc){
        this.loc = loc;
    }

}

