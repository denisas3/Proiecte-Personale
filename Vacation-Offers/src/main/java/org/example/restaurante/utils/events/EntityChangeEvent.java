package org.example.restaurante.utils.events;

public class EntityChangeEvent implements Event {
    private final ChangeEventType type;
    private final Object data;   // poate fi Masina, Detaliu, etc.

    public EntityChangeEvent(ChangeEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
