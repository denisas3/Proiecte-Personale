package curs3.io.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Event extends Entity<Long>{

    private String numeEvent;
    private List<Utilizator> subscribers;

    public Event(Long id, String numeEvent) {
        this.id = id;
        this.numeEvent = numeEvent;
        this.subscribers = new ArrayList<Utilizator>();
    }

    public String getNumeEvent() {
        return numeEvent;
    }

    public void setNumeEvent(String numeEvent) {
        this.numeEvent = numeEvent;
    }

    public List<Utilizator> getSubscribers() {
        return subscribers;
    }

    public abstract void subscribe(Utilizator utilizator);
    public abstract void unsubscribe(Utilizator utilizator);
    public abstract void notifySubscribers(Utilizator utilizator);

}
