package org.example.lab03;

import java.time.LocalDateTime;

public class Show extends Entity<Integer>{

    private String artistName;
    private LocalDateTime date;
    private String location;
    private Integer availableSeats;
    private Integer soldSeats;

    public Show(String artistName, LocalDateTime date, String location, Integer availableSeats, Integer soldSeats) {
        this.artistName = artistName;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }

    public String  getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    public Integer getSoldSeats() {
        return soldSeats;
    }
    public void setSoldSeats(Integer soldSeats) {
        this.soldSeats = soldSeats;
    }
    public boolean isSoldOut() {
        return availableSeats != null && soldSeats != null && soldSeats >= availableSeats;
    }

    @Override
    public String toString() {
        return artistName + " - " + location + " - " + date;
    }
}
