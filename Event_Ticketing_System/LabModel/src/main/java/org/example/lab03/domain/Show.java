//package org.example.lab03.domain;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//@Table(name ="shows")
//public class Show{
//
//    private Long id;
//    private String artistName;
//    private LocalDateTime date;
//    private String location;
//    private Integer availableSeats;
//    private Integer soldSeats;
//
//    public Show(){}
//
//    public Show(String artistName, LocalDateTime date, String location, Integer availableSeats, Integer soldSeats) {
//        this.artistName = artistName;
//        this.date = date;
//        this.location = location;
//        this.availableSeats = availableSeats;
//        this.soldSeats = soldSeats;
//    }
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    @NotNull
//    public String  getArtistName() {
//        return artistName;
//    }
//    public void setArtistName(String artistName) {
//        this.artistName = artistName;
//    }
//    @NotNull
//    public LocalDateTime getDate() {
//        return date;
//    }
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//    @NotNull
//    public String getLocation() {
//        return location;
//    }
//    public void setLocation(String location) {
//        this.location = location;
//    }
//    @NotNull
//    public Integer getAvailableSeats() {
//        return availableSeats;
//    }
//    public void setAvailableSeats(Integer availableSeats) {
//        this.availableSeats = availableSeats;
//    }
//    @NotNull
//    public Integer getSoldSeats() {
//        return soldSeats;
//    }
//    public void setSoldSeats(Integer soldSeats) {
//        this.soldSeats = soldSeats;
//    }
//
//    public boolean isSoldOut() {
//        return availableSeats != null && soldSeats != null && soldSeats >= availableSeats;
//    }
//
//    @Override
//    public String toString() {
//        return "Show [ id=" + id +", artistName=" + artistName + ", date=" + date + ", location=" + location + ", availableSeats=" + availableSeats + ", soldSeats=" + soldSeats + "]";
//    }
//
//}
package org.example.lab03.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@jakarta.persistence.Entity
@Table(name = "shows")
public class Show extends org.example.lab03.domain.Entity<Long> {

    private String artistName;
    private LocalDateTime date;
    private String location;
    private Integer availableSeats;
    private Integer soldSeats;

    public Show() {
    }

    public Show(String artistName, LocalDateTime date, String location,
                Integer availableSeats, Integer soldSeats) {
        this.artistName = artistName;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }

    @Override
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_show")
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @NotNull
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @NotNull
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @NotNull
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NotNull
    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @NotNull
    public Integer getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(Integer soldSeats) {
        this.soldSeats = soldSeats;
    }

    @Override
    public String toString() {
        return "Show [ id=" + getId() +
                ", artistName=" + artistName +
                ", date=" + date +
                ", location=" + location +
                ", availableSeats=" + availableSeats +
                ", soldSeats=" + soldSeats + "]";
    }
}