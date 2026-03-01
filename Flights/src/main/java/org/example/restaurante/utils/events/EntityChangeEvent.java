package org.example.restaurante.utils.events;

import org.example.restaurante.domain.Bilet;
import org.example.restaurante.domain.Zbor;

public class EntityChangeEvent implements Event {
    private ChangeEventType type;
    private Bilet data;

    public EntityChangeEvent(ChangeEventType type, Bilet data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Bilet getData() {
        return data;
    }

}