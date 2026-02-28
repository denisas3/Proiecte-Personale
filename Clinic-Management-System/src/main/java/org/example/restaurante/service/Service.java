package org.example.restaurante.service;

import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.Medic;
import org.example.restaurante.domain.Sectie;
import org.example.restaurante.repository.ConsultatieRepository;
import org.example.restaurante.repository.MedicRepository;
import org.example.restaurante.repository.SectieRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.util.List;

import static org.example.restaurante.utils.events.ChangeEventType.ADD;

public class Service implements Observable<EntityChangeEvent> {
    SectieRepository sectieRepository;
    MedicRepository medicRepository;
    ConsultatieRepository consultatieRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(SectieRepository sectieRepository, MedicRepository medicRepository, ConsultatieRepository consultatieRepository) {
        this.sectieRepository = sectieRepository;
        this.medicRepository = medicRepository;
        this.consultatieRepository = consultatieRepository;
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

   public List<Sectie> getSectii(){
        return sectieRepository.findAll();
   }

   public List<Medic> getMedici(){
        return medicRepository.findAll();
   }

   public List<Consultatie> getConsultatie(){
        return consultatieRepository.findAll();
   }

    public Integer findMedicIdByNameAndIdSectie(String medicName, Integer id) {
        return medicRepository.findAll().stream()
                .filter(m -> m.getNume().equalsIgnoreCase(medicName) && m.getId_sectie().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Medic inexistent: " + medicName))
                .getId();
    }

    public void saveConsultatie(Consultatie consultatie){
        consultatieRepository.save(consultatie);
        notifyObservers(new EntityChangeEvent(ADD,consultatie));
   }


}
