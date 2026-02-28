package org.example.restaurante.service;

import org.example.restaurante.domain.Detaliu;
import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.User;
import org.example.restaurante.repository.DetaliuRepository;
import org.example.restaurante.repository.MasinaRepository;
import org.example.restaurante.repository.UserRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.util.List;

import static org.example.restaurante.utils.events.ChangeEventType.ADD;
import static org.example.restaurante.utils.events.ChangeEventType.UPDATE;

public class Service implements Observable<EntityChangeEvent> {
    UserRepository userRepository;
    MasinaRepository masinaRepository;
    DetaliuRepository detaliuRepository;
    List<Observer<EntityChangeEvent>> observers;

    public Service(UserRepository userRepository, MasinaRepository masinaRepository,  DetaliuRepository detaliuRepository) {
        this.userRepository = userRepository;
        this.masinaRepository = masinaRepository;
        this.detaliuRepository = detaliuRepository;
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

    public List<Masina> getMasini() {
        return masinaRepository.findAll();
    }

    /**
     * Autentifică un utilizator
     * @param username username-ul
     * @param password parola
     * @return User dacă autentificarea reușește, null altfel
     */
    public User authenticate(String username, String password) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public List<Detaliu> getDetalii() {
        return detaliuRepository.findAll();
    }

    public List<Masina> getMasiniCuAnumitStatus() {
        return masinaRepository.findAll().stream()
                .filter(m->m.getStatur_curent().equals(Status.NEEDS_APPROVAL))
                .toList();
    }

    public Detaliu getDetaliu(int id_masina) {
        return detaliuRepository.findAll().stream()
                .filter(d->d.getId_masina().equals(id_masina))
                .findFirst()
                .orElse(null);
    }

    public void update_detaliu(Detaliu detaliu) {
        Detaliu det = detaliuRepository.findAll().stream()
                        .filter( d->d.getId_masina().equals(detaliu.getId_masina()) &&
                                d.getDetaliu().equals(detaliu.getDetaliu()))
                                .findFirst()
                                        .orElse(null);
        if(det!=null) {
            det.setComentariu(detaliu.getComentariu());
            detaliuRepository.update(det);
            notifyObservers(new EntityChangeEvent(UPDATE, detaliu));
        }
        else
        {
            detaliuRepository.save(detaliu);
            notifyObservers(new EntityChangeEvent(ADD, detaliu));
        }
    }

    public void update_masina(Masina masina) {
        masinaRepository.update(masina);
        notifyObservers(new EntityChangeEvent(UPDATE, masina));
    }

}

