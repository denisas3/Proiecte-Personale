package org.example.restaurante.utils.events;

import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.Medic;

public class EntityChangeEvent implements Event {
    private ChangeEventType type;
    private Consultatie data;

    public EntityChangeEvent(ChangeEventType type, Consultatie data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Consultatie getData() {
        return data;
    }

}