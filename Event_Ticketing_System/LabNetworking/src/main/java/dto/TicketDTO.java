package dto;

import org.example.lab03.domain.Show;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TicketDTO implements Serializable {
    private Long id;
    private String buyerName;
    private Integer seatCount;
    private LocalDateTime soldAt;
    private Show show;

    public TicketDTO(Long id, String buyerName, Integer seatCount, LocalDateTime soldAt, org.example.lab03.domain.Show show) {
        this.id = id;
        this.buyerName = buyerName;
        this.seatCount = seatCount;
        this.soldAt = soldAt;
        this.show = show;
    }
    public Long getId() {
        return id;
    }
    public String getBuyerName() {
        return buyerName;
    }
    public Integer getSeatCount() {
        return seatCount;
    }
    public LocalDateTime getSoldAt() {
        return soldAt;
    }
    public Show getShow() {
        return show;
    }
    public String toString() {
        return "TicketDTO[" + " buyerName:" + buyerName + " seatCount:" +  seatCount + " soldAt:" + soldAt + " show:" + show + "]";
    }

}
