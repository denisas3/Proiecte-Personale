package org.example.ati.utils.observer;

import org.example.ati.utils.events.Event;

public interface Observable<E  extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E event);
}
