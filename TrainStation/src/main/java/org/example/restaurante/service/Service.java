package org.example.restaurante.service;

import org.example.restaurante.domain.Oras;
import org.example.restaurante.domain.Statie;
import org.example.restaurante.repository.TrenRepository;
import org.example.restaurante.repository.StatieRepository;
import org.example.restaurante.repository.OrasRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.util.List;


public class Service implements Observable<EntityChangeEvent> {
    OrasRepository orasRepository;
    StatieRepository statieRepository;
    TrenRepository actiuneRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(OrasRepository orasRepository, StatieRepository statieRepository, TrenRepository actiuneRepository) {
        this.orasRepository = orasRepository;
        this.statieRepository = statieRepository;
        this.actiuneRepository = actiuneRepository;
        observers = new java.util.ArrayList<>();
    }

    @Override
    public void addObserver(Observer<EntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EntityChangeEvent event) {
        observers.forEach(o -> o.update(event));
    }

    public List<Oras> getOras(){
        return this.orasRepository.findAll();
    }

    public List<Statie>  getStatie(){
        return this.statieRepository.findAll();
    }

    /// PENTRU COMBO BOX
    public List<String> fillComboBoxOras()
    {
        var orase = orasRepository.findAll();

        var fromList = orase.stream()
                .map(Oras::getNume)
                .distinct()
                .toList();

        return fromList;
    }

    public record Step(int fromCityId, int trainId, int toCityId) {}

    public List<List<Step>> findRoutes(int startCityId, int endCityId, boolean directOnly) {
        List<Statie> edges = statieRepository.findAll();

        java.util.Map<Integer, List<Statie>> adj = new java.util.HashMap<>();
        for (Statie s : edges) {
            adj.computeIfAbsent(s.getId_oras_plecare(), k -> new java.util.ArrayList<>()).add(s);
        }

        List<List<Step>> result = new java.util.ArrayList<>();
        java.util.Set<Integer> visitedCities = new java.util.HashSet<>();
        visitedCities.add(startCityId);

        backtrack(startCityId, endCityId, directOnly, null, adj, visitedCities, new java.util.ArrayList<>(), result);
        return result;
    }

    private void backtrack(
            int current,
            int target,
            boolean directOnly,
            Integer currentTrain,
            java.util.Map<Integer, List<Statie>> adj,
            java.util.Set<Integer> visited,
            java.util.List<Step> path,
            java.util.List<List<Step>> result
    ) {
        if (current == target) {
            result.add(new java.util.ArrayList<>(path));
            return;
        }

        for (Statie s : adj.getOrDefault(current, java.util.List.of())) {
            int nextCity = s.getId_oras_sosier();

            if (visited.contains(nextCity)) continue;

            if (directOnly) {
                if (currentTrain != null && !currentTrain.equals(s.getId_tren())) continue;
            }

            visited.add(nextCity);
            path.add(new Step(current, s.getId_tren(), nextCity));

            backtrack(nextCity, target, directOnly,
                    currentTrain == null ? s.getId_tren() : currentTrain,
                    adj, visited, path, result);

            path.remove(path.size() - 1);
            visited.remove(nextCity);
        }
    }


    public int getOrasIdByName(String name) {
        return orasRepository.findAll().stream()
                .filter(o -> o.getNume().equals(name))
                .findFirst()
                .orElseThrow()
                .getId();
    }

    public String getOrasNameById(int id) {
        return orasRepository.findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow()
                .getNume();
    }

    private static final double PRICE_PER_STATION = 10.0;

    public String formatRoute(List<Step> route) {
        if (route.isEmpty()) return "(aceeasi destinatie), price: 0.0";

        double price = PRICE_PER_STATION * route.size();

        StringBuilder sb = new StringBuilder();
        sb.append(getOrasNameById(route.get(0).fromCityId()));

        for (Step st : route) {
            sb.append(" -T").append(st.trainId()).append("-> ");
            sb.append(getOrasNameById(st.toCityId()));
        }

        sb.append(", price: ").append(price);
        return sb.toString();
    }

}
