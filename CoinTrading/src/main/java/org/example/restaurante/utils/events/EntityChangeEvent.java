package org.example.restaurante.utils.events;

import org.example.restaurante.domain.Coins;

public class EntityChangeEvent implements Event {
    private ChangeEventType type;
    private Coins data;

    public EntityChangeEvent(ChangeEventType type, Coins data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Coins getData() {
        return data;
    }

}