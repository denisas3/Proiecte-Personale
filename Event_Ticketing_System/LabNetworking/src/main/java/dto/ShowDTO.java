package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShowDTO implements Serializable {
    private Long id;
    private String artistName;
    private LocalDateTime date;
    private String location;
    private Integer availableSeats;
    private Integer soldSeats;

    public ShowDTO(Long id, String artistName, LocalDateTime date, String location, Integer availableSeats, Integer soldSeats) {
        this.id = id;
        this.artistName = artistName;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }
    public Long getId() {
        return id;
    }
    public String getArtistName() {
        return artistName;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getLocation() {
        return location;
    }
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    public Integer getSoldSeats() {
        return soldSeats;
    }
    public String toString() {
        return "ShowDTO[" + "artistName:" + artistName + ", date:" + date  + ", location:" + location + ", availableSeats:" + availableSeats  + ", soldSeats:" + soldSeats + ']';
    }
}
