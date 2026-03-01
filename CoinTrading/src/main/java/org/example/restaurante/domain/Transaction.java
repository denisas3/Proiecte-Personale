package org.example.restaurante.domain;

import java.time.LocalDateTime;

public class Transaction extends Entity<Integer>{
    private Integer userId;
    private Integer coinId;
    private Status type;
    private Double price;
    private LocalDateTime timestamp;

   public Transaction(Integer userId, Integer coinId, Status type, Double price, LocalDateTime timestamp) {
        this.userId = userId;
        this.coinId = coinId;
        this.type = type;
        this.price = price;
        this.timestamp = timestamp;
   }

   public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCoinId() {
       return coinId;
    }
    public void setCoinId(Integer coinId) {
       this.coinId = coinId;
    }

    public Status getType() {
       return type;
    }
    public void setType(Status type) {
       this.type = type;
    }

    public Double getPrice() {
       return price;
    }
    public void setPrice(Double price) {
       this.price = price;
    }

    public LocalDateTime getTimestamp() {
       return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
       this.timestamp = timestamp;
    }

}
