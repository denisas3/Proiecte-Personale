//package org.example.lab03.domain;
//
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import org.example.lab03.domain.Show;
//
//import jakarta.validation.constraints.NotNull;
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "tickets")
//public class Ticket {
//
//    private Long id;
//    private String buyerName;
//    private Integer seatCount;
//    private LocalDateTime soldAt;
//    private org.example.lab03.domain.Show show;
//
//    public Ticket() {}
//
//    public Ticket(String buyerName, Integer seatCount, LocalDateTime soldAt, org.example.lab03.domain.Show show) {
//        this.buyerName = buyerName;
//        this.seatCount = seatCount;
//        this.soldAt = soldAt;
//        this.show = show;
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
//    public String getBuyerName() {
//        return buyerName;
//    }
//    public void setBuyerName(String buyerName) {
//        this.buyerName = buyerName;
//    }
//    @NotNull
//    public Integer getSeatCount() {
//        return seatCount;
//    }
//    public void setSeatCount(Integer seatCount) {
//        this.seatCount = seatCount;
//    }
//    @NotNull
//    public LocalDateTime getSoldAt() {
//        return soldAt;
//    }
//    public void setSoldAt(LocalDateTime soldAt) {this.soldAt = soldAt;}
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "show_id")
//    public Show getShow() {return show;}
//    public void setShow(org.example.lab03.domain.Show show) {this.show = show;}
//    @Override
//    public String toString() {
//        return "Ticket [ id=" + id +", buyerName=" + buyerName + ", seatCount=" + seatCount + ", soldAt=" + soldAt + ", show=" + show + "]";
//    }
//
//}
package org.example.lab03.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@jakarta.persistence.Entity
@Table(name = "tickets")
public class Ticket extends org.example.lab03.domain.Entity<Long> {

    private String buyerName;
    private Integer seatCount;
    private LocalDateTime soldAt;
    private Show show;

    public Ticket() {
    }

    public Ticket(String buyerName,
                  Integer seatCount,
                  LocalDateTime soldAt,
                  Show show) {

        this.buyerName = buyerName;
        this.seatCount = seatCount;
        this.soldAt = soldAt;
        this.show = show;
    }

    @Override
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_ticket")
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @NotNull
    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @NotNull
    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    @NotNull
    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_show")
    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Ticket [ id=" + getId() +
                ", buyerName=" + buyerName +
                ", seatCount=" + seatCount +
                ", soldAt=" + soldAt +
                ", show=" + show + "]";
    }
}