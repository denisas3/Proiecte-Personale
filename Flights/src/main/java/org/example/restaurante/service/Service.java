package org.example.restaurante.service;

import org.example.restaurante.domain.Bilet;
import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.Zbor;
import org.example.restaurante.repository.BiletRepository;
import org.example.restaurante.repository.ZborRepository;
import org.example.restaurante.repository.ClientRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;
import org.example.restaurante.utils.paging.Page;
import org.example.restaurante.utils.paging.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.example.restaurante.utils.events.ChangeEventType.UPDATE;

public class Service implements Observable<EntityChangeEvent> {
    ClientRepository clientRepository;
    ZborRepository zborRepository;
    BiletRepository biletRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(ClientRepository clientRepository, ZborRepository zborRepository, BiletRepository biletRepository) {
        this.clientRepository = clientRepository;
        this.zborRepository = zborRepository;
        this.biletRepository = biletRepository;
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

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public List<Zbor> getZbores() {
        return zborRepository.findAll()
                .stream()
                .distinct()
                .sorted(java.util.Comparator.comparing(Zbor::getDecolare))
                .toList();
    }

    public Client findClientByUsername(String username){
        List<Client> clienti = clientRepository.findAll();
        return clienti.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /// PENTRU COMBO BOX FROM
    public List<String> fillComboBoxFrom()
    {
        var zboruri = zborRepository.findAll();

        var fromList = zboruri.stream()
                .map(Zbor::getDe_unde)
                .distinct()
                .toList();

        return fromList;
    }

    /// PENTRU COMBO BOX FROM
    public List<String> fillComboBoxTo()
    {
        var zboruri = zborRepository.findAll();

        var toList = zboruri.stream()
                .map(Zbor::getPana_unde)
                .distinct()
                .toList();

        return toList;
    }

    /// PENTRU BUTONUL FILTRARE
    public List<Zbor> filterZboruri(String from, String to, LocalDate date) {
        return zborRepository.findAll().stream()
                .filter(z -> from == null || z.getDe_unde().equals(from))
                .filter(z -> to == null || z.getPana_unde().equals(to))
                .filter(z -> date == null ||
                        z.getDecolare().toLocalDate().equals(date))
                .toList();
    }

    /// PENTRU CUMPARAREA BILETULUI
    public void saveBilet(Bilet bilet){
        biletRepository.save(bilet);
        notifyObservers(new EntityChangeEvent(UPDATE,bilet));
    }

    /// PENTRU A VEDEA CATE LOCURI MAI SUNT IN AVION
    public int getLocuriDisponibile(Zbor zbor) {
        int cumparate = biletRepository.countByZborId(zbor.getId());
        return zbor.getLoc() - cumparate;
    }


    /// PENTRU PAGINARE
    public Page<Zbor> getZboruriPage(String from, String to, LocalDate date, Pageable pageable) {
        return zborRepository.findPageFiltered(from, to, date, pageable);
    }

}
