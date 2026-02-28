package curs3.io.domain;

import java.util.List;

public class RaceEvent extends Event{

    public RaceEvent(Long id, String name) {
        super(id, name);
    }

    @Override
    public void subscribe(Utilizator utilizator) {
        getSubscribers().add(utilizator);
    }

    @Override
    public void unsubscribe(Utilizator utilizator) {
        getSubscribers().remove(utilizator);
    }

    @Override
    public void notifySubscribers(Utilizator utilizator) {

    }

    public String toString() {
        return "RaceEvent{ " +
                "id: " + this.getId() +
                ", nume: " + this.getNumeEvent() +
                ", subscribers: " + this.getSubscribers() +
                "}";
    }

}
