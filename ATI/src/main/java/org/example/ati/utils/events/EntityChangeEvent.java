package org.example.ati.utils.events;

import org.example.ati.domain.Pat;

public class EntityChangeEvent implements Event {
    private ChangeEventType type;
    private Pat data;

    public EntityChangeEvent(ChangeEventType type, Pat data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Pat getData() {
        return data;
    }

}