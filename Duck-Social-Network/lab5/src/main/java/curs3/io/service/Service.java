package curs3.io.service;

import curs3.io.domain.Utilizator;
import curs3.io.repository.InMemoryRepository;
import curs3.io.ui.Ui;

import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private InMemoryRepository<Long, Utilizator> repo;

    public Service(InMemoryRepository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public void addUtilizator(Utilizator utilizator) throws Exception {
        try {
            Utilizator existing = repo.save(utilizator);
            if (existing == utilizator) {
                throw new Exception("Utilizator already exists!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public void removeUtilizator(Long id) throws Exception {
        try{
            Utilizator utilizator = repo.findOne(id);
            if(repo.delete(id) == null)
                throw new Exception("Utilizator doesn't exist!");
            repo.removeUserAndRelations(utilizator);
            System.out.println("Utilizator has been successfully removed!");
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void printAll() {
        repo.findAll().forEach(System.out::println);
    }

    public void addFriend(Long id1, Long id2) throws Exception {
        Utilizator utilizator1 = repo.findOne(id1);
        Utilizator utilizator2 = repo.findOne(id2);
        if(utilizator1 != null && utilizator2 != null)
            repo.addFriendRepo(utilizator1,utilizator2);
        else throw new Exception("Utilizator doesn't exist!");
    }

    public void removeFriend(Long id1, Long id2) throws Exception {
        Utilizator utilizator1 = repo.findOne(id1);
        Utilizator utilizator2 = repo.findOne(id2);
        if(utilizator1 != null && utilizator2 != null)
            repo.removeFriendRepo(utilizator1,utilizator2);
        else throw new Exception("Utilizator doesn't exist!");
    }

    public int getNumarComunitati() {
        List<Utilizator> totiUtilizatorii = new ArrayList<>();
        repo.findAll().forEach(totiUtilizatorii::add);

        if (totiUtilizatorii.isEmpty()) {
            return 0;
        }

        List<List<Utilizator>> toateComunitatile = gasesteComunitati(totiUtilizatorii);

        return toateComunitatile.size();
    }

    public List<List<Utilizator>> gasesteComunitati(List<Utilizator> totiUtilizatorii) {
        Map<Long, Utilizator> utilizatoriMap = totiUtilizatorii.stream()
                .collect(Collectors.toMap(Utilizator::getId, u -> u));

        Set<Long> vizitati = new HashSet<>();
        List<List<Utilizator>> comunitati = new ArrayList<>();

        for (Utilizator u : totiUtilizatorii) {
            if (!vizitati.contains(u.getId())) {
                List<Utilizator> comunitateCurenta = new ArrayList<>();

                dfsComunitate(u, vizitati, comunitateCurenta, utilizatoriMap);

                comunitati.add(comunitateCurenta);
            }
        }
        return comunitati;
    }

    private void dfsComunitate(Utilizator curent, Set<Long> vizitati, List<Utilizator> comunitate, Map<Long, Utilizator> utilizatoriMap) {
        vizitati.add(curent.getId());
        comunitate.add(curent);

        if (curent.getFriends() != null) {
            for (Utilizator prieten : curent.getFriends()) {
                Utilizator prietenComplet = utilizatoriMap.get(prieten.getId());
                if (prietenComplet != null && !vizitati.contains(prietenComplet.getId())) {
                    dfsComunitate(prietenComplet, vizitati, comunitate, utilizatoriMap);
                }
            }
        }
    }

    public Utilizator findDuck(Long id) throws Exception{
        Utilizator utilizator1 = repo.findOne(id);
        if(utilizator1 == null) throw new Exception("Duck doesn't exist!");
        return utilizator1;
    }

    public Utilizator findPersoana(Long id) throws Exception{
        Utilizator utilizator1 = repo.findOne(id);
        if(utilizator1 == null) throw new Exception("Persoana doesn't exist!");
        return utilizator1;
    }
}
