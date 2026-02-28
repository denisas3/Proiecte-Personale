package curs3.io.repository;

import java.util.ArrayList;
import java.util.List;

public class PageObservable {
    private List<PageObserver> observers = new ArrayList<>();

    public void addObserver(PageObserver obs) {
        observers.add(obs);
    }

    public void notifyObservers(int page, int totalPages) {
        for (PageObserver o : observers) {
            o.onPageChanged(page, totalPages);
        }
    }
}
