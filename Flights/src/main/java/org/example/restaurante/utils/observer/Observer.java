package org.example.restaurante.utils.observer;

import org.example.restaurante.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E event);
}
