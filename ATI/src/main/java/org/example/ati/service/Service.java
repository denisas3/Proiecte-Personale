package org.example.ati.service;

import org.example.ati.domain.Pacient;
import org.example.ati.domain.Pat;
import org.example.ati.repository.PatRepository;
import org.example.ati.repository.PacientRepository;
import org.example.ati.utils.observer.Observer;
import org.example.ati.utils.events.EntityChangeEvent;
import org.example.ati.utils.observer.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Locale.filter;
import static org.example.ati.utils.events.ChangeEventType.ADD;

public class Service implements Observable<EntityChangeEvent> {
    PacientRepository pacientRepository;
    PatRepository patRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(PacientRepository pacientRepository, PatRepository patRepository) {
        this.pacientRepository = pacientRepository;
        this.patRepository = patRepository;
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

    public List<Pacient> getPacienti() {
        return pacientRepository.findAll();
    }

    public List<Pat> getPaturi() {
        return patRepository.findAll();
    }

    public long getNrPaturiOcupate() {
        return patRepository.findAll().stream()
                .filter(p -> p.getId_pacient() != null)
                .count();
    }

    private static final int NR_PAT_TIC = 14;
    private static final int NR_PAT_TIM = 8;
    private static final int NR_PAT_TIIP = 8;

    public Map<String, Integer> getNrPaturiDisponibile() {
        List<Pat> paturi = patRepository.findAll();

        long ocupateTIC = paturi.stream()
                .filter(p -> "TIC".equals(p.getTip()) && p.getId_pacient() != null)
                .count();

        long ocupateTIM = paturi.stream()
                .filter(p -> "TIM".equals(p.getTip()) && p.getId_pacient() != null)
                .count();

        long ocupateTIIP = paturi.stream()
                .filter(p -> "TIIP".equals(p.getTip()) && p.getId_pacient() != null)
                .count();

        Map<String, Integer> map = new HashMap<>();
        map.put("TIC", (int)(NR_PAT_TIC - ocupateTIC));
        map.put("TIM", (int)(NR_PAT_TIM - ocupateTIM));
        map.put("TIIP",(int)(NR_PAT_TIIP - ocupateTIIP));
        return map;
    }

    public List<Pacient> getPacientiInAsteptare() {
        List<Pat> paturi = patRepository.findAll();
        List<Integer> pacientiCuPat = patRepository.findAll().stream()
                .filter(p -> p.getId_pacient() != null)
                .map(Pat::getId_pacient)
                .toList();

        return pacientRepository.findAll().stream()
                .filter(p -> !pacientiCuPat.contains(p.getId()))
                .sorted((p1, p2) -> p2.getGravitate().compareTo(p1.getGravitate()))
                .toList();
    }

    public Long getNrPaturiDisponibileTIC() {
        List<Pat> paturi = patRepository.findAll();
        Long nr_pat_ocupate = paturi.stream()
                .filter(p->p.getTip().equals("TIC"))
                .count();
        for(Pat pat:paturi){
            if(pat.getTip().equals("TIC")){
                return pat.getNr_pat_disponibile() - nr_pat_ocupate; }
        }
        return null;
    }

    public Long getNrPaturiDisponibileTIM() {
        List<Pat> paturi = patRepository.findAll();
        Long nr_pat_ocupate = paturi.stream()
                .filter(p->p.getTip().equals("TIM"))
                .count();
        for(Pat pat:paturi){
            if(pat.getTip().equals("TIM")){
                return pat.getNr_pat_disponibile() - nr_pat_ocupate; }
        }
        return null;
    }

    public Long getNrPaturiDisponibileTIIP() {
        List<Pat> paturi = patRepository.findAll();
        Long nr_pat_ocupate = paturi.stream()
                .filter(p->p.getTip().equals("TIIP"))
                .count();
        for(Pat pat:paturi){
            if(pat.getTip().equals("TIIP")){
                return pat.getNr_pat_disponibile() - nr_pat_ocupate; }
        }
        return null;
    }

    public Pacient findPacient(Integer cnp) {
        List<Pacient> pacienti = pacientRepository.findAll();
        return pacienti.stream()
                .filter(p -> p.getCnp() != null && p.getCnp().equals(cnp))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nu exista pacient cu CNP=" + cnp));
    }

    public void savePat(Pat pat) {
        patRepository.save(pat);
        notifyObservers(new EntityChangeEvent(ADD,pat));
    }
}
