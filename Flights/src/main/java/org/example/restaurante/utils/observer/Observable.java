package org.example.restaurante.utils.observer;

import org.example.restaurante.utils.events.Event;

public interface Observable<E  extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E event);
}
