package org.example.restaurante.domain;

import java.time.LocalDateTime;


/// DTO PT OBIECTUL CE TREBUIE AFISAT IN TABEL
public class PlacedOrderRow {
    private final Integer tableId;
    private final LocalDateTime date;
    private final String items; // "Soup, Steak, Cake"

    public PlacedOrderRow(Integer tableId, LocalDateTime date, String items) {
        this.tableId = tableId;
        this.date = date;
        this.items = items;
    }

    public Integer getTableId() { return tableId; }
    public LocalDateTime getDate() { return date; }
    public String getItems() { return items; }
}
