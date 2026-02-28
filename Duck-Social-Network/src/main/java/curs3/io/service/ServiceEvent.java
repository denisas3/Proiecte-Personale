package curs3.io.service;

import curs3.io.domain.Duck;
import curs3.io.domain.Event;
import curs3.io.domain.Utilizator;
import curs3.io.repository.EventRepository;
import curs3.io.repository.EventSubscribersRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceEvent {

    private final EventRepository eventRepo;
    private final EventSubscribersRepository subscribersRepo;

    public ServiceEvent(EventRepository eventRepo, EventSubscribersRepository subscribersRepo) {
        this.eventRepo = eventRepo;
        this.subscribersRepo = subscribersRepo;
    }

    public void addEvent(Event e) {
        eventRepo.save(e);
    }

    public void removeEvent(Long id) {
        eventRepo.delete(id);
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public void addSubscriber(Long eventID, Long userID) {
        subscribersRepo.addSubscriber(eventID, userID);
    }

    public void removeSubscriber(Long eventID, Long userID) {
        subscribersRepo.removeSubscriber(eventID, userID);
    }

    public List<Utilizator> getSubscribers(Long eventID) {
        return subscribersRepo.getSubscribers(eventID);
    }

    public List<Duck> selectTopDucks(Long nr) {
        var events = eventRepo.findAll();

        var allSubs = events.stream()
                .flatMap(e -> subscribersRepo.getSubscribers(e.getId()).stream())
                .toList();

        var ducks = allSubs.stream()
                .filter(u -> u instanceof Duck)
                .map(u -> (Duck) u)
                .sorted(
                        Comparator.comparingDouble(Duck::getResistance).reversed()
                                .thenComparing(Comparator.comparingDouble(Duck::getSpeed).reversed())
                )
                .collect(Collectors.toList());

        if (ducks.size() < nr)
            throw new RuntimeException("Nu exista suficiente rate!");

        return ducks.subList(0, nr.intValue());
    }
}
