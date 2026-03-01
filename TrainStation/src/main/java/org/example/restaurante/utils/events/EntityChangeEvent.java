package org.example.restaurante.utils.events;

import org.example.restaurante.domain.Statie;

public class EntityChangeEvent implements Event {
    private ChangeEventType type;
    private Statie data;

    public EntityChangeEvent(ChangeEventType type, Statie data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Statie getData() {
        return data;
    }

}