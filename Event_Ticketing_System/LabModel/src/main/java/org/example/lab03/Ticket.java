//package org.example.lab03;
//
//import org.example.lab03.domain.Entity;
//
//import java.time.LocalDateTime;
//
//public class Ticket extends Entity<Integer> {
//
//    private String buyerName;
//    private Integer seatCount;
//    private LocalDateTime soldAt;
//    private Show show;
//
//    public Ticket(String buyerName, Integer seatCount, LocalDateTime soldAt, Show show) {
//        this.buyerName = buyerName;
//        this.seatCount = seatCount;
//        this.soldAt = soldAt;
//        this.show = show;
//    }
//
//    public String getBuyerName() {
//        return buyerName;
//    }
//    public void setBuyerName(String buyerName) {
//        this.buyerName = buyerName;
//    }
//    public Integer getSeatCount() {
//        return seatCount;
//    }
//    public void setSeatCount(Integer seatCount) {
//        this.seatCount = seatCount;
//    }
//    public LocalDateTime getSoldAt() {
//        return soldAt;
//    }
//    public void setSoldAt(LocalDateTime soldAt) {this.soldAt = soldAt;}
//    public Show getShow() {return show;}
//    public void setShow(Show show) {this.show = show;}
//}