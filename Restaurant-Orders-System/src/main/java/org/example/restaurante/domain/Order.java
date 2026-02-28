package org.example.restaurante.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Integer>{
    private int table;
    private LocalDateTime date;
    private OrderStatus status;

    public Order(int table, LocalDateTime date, OrderStatus status) {
        this.table = table;
        this.date = date;
        this.status = status;
    }

    public int getTable() {
        return table;
    }
    public void setTable(int table) {
        this.table = table;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public OrderStatus getStatus() {
        return status;
    }
}
