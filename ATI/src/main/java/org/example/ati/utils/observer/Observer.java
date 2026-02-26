package org.example.ati.utils.observer;

import org.example.ati.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E event);
}
