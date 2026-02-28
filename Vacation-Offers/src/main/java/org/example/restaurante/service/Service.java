package org.example.restaurante.service;

import org.example.restaurante.domain.*;
import org.example.restaurante.repository.*;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.time.LocalDate;
import java.util.List;

import static org.example.restaurante.utils.events.ChangeEventType.ADD;

public class Service implements Observable<EntityChangeEvent> {
    LocatieRepository locatieRepository;
    HotelRepository hotelRepository;
    OferteSpecialeRepository ofertaRepository;
    ClientRepository clientRepository;
    RezervareRepository rezervareRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(LocatieRepository locatieRepository, HotelRepository hotelRepository,
                   OferteSpecialeRepository ofertaRepository, ClientRepository clientRepository,
                   RezervareRepository rezervareRepository) {
        this.locatieRepository = locatieRepository;
        this.hotelRepository = hotelRepository;
        this.ofertaRepository = ofertaRepository;
        this.clientRepository = clientRepository;
        this.rezervareRepository = rezervareRepository;
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

    public List<String> getLocatiiDistincte() {
        return locatieRepository.findAll().stream()
                .distinct()
                .map(Locatie::getNume)
                .toList();
    }

    public List<Hotel> getHoteluri() {
        return hotelRepository.findAll();
    }

    public Locatie getLocatieDupaNume(String locatie){
        return locatieRepository.findAll().stream()
                .filter(l->l.getNume().equals(locatie))
                .findFirst()
                .orElse(null);
    }

    public List<Hotel> getHoteluriDupaLocatie(Integer id_locatie){
        return hotelRepository.findAll().stream()
                .filter(h->h.getId_locatie().equals(id_locatie))
                .toList();
    }

    public List<OferteSpeciale> getOferteSpeciale(){
        return ofertaRepository.findAll();
    }

    public List<OferteSpeciale> findOfertaDupaDate(LocalDate data_i, LocalDate data_f, Integer id_hotel){
        return ofertaRepository.findAll().stream()
                .filter(f -> f.getId_hotel().equals(id_hotel))
                .filter(f ->
                        !f.getData_inceput().isBefore(data_i) &&  // start >= data_i
                        !f.getData_final().isAfter(data_f)        // end <= data_f
                )
                .filter(f->f.getId_hotel().equals(id_hotel))
                .toList();
    }

    public List<Client> getClienti(){
        return clientRepository.findAll();
    }


    /// PENTRU AFISEAREA OFERTELOR PT CLIENTI
    ///
    public List<AfisareOferetRow> getAfisareOfereteByClient(Integer idClient) {
        Client client = clientRepository.findAll().stream()
                .filter(c -> c.getId().equals(idClient))
                .findFirst()
                .orElse(null);
        if (client == null) return List.of();

        LocalDate azi = LocalDate.now();
        int grad = client.getGrad_fidelitate();

        var hotelById = hotelRepository.findAll().stream()
                .collect(java.util.stream.Collectors.toMap(Hotel::getId, h -> h));

        var locatieById = locatieRepository.findAll().stream()
                .collect(java.util.stream.Collectors.toMap(Locatie::getId, l -> l));

        return ofertaRepository.findAll().stream()
                .filter(of -> !of.getData_final().isBefore(azi))
                .filter(of -> grad > of.getProcent())
                .map(of -> {
                    Hotel h = hotelById.get(of.getId_hotel());
                    if (h == null) return null;

                    Locatie loc = locatieById.get(h.getId_locatie());
                    if (loc == null) return null;

                    return new AfisareOferetRow(
                            h.getHotel_name(),
                            loc.getNume(),
                            of.getData_inceput(),
                            of.getData_final()
                    );
                })
                .filter(java.util.Objects::nonNull)
                .sorted(java.util.Comparator
                        .comparing(AfisareOferetRow::getNume_hotel)
                        .thenComparing(AfisareOferetRow::getData_inceput))
                .toList();
    }

    public Hotel getIdHotelByNume(String nume){
        return hotelRepository.findAll().stream()
                .filter(h->h.getHotel_name().equals(nume))
                .findFirst()
                .orElse(null);
    }


    /// SAVE REZERVARE
    public void save_rezervare(Rezervare rezervare){
        try {
            rezervareRepository.save(rezervare);
            notifyObservers(new EntityChangeEvent(ADD, rezervare));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    public List<Rezervare> getRezervariAleClientului(Integer id_client){
        return rezervareRepository.findAll().stream()
                .filter(r -> r.getId_client().equals(id_client))
                .toList();
    }


    public Client getClientById(Integer id) {
        return clientRepository.findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findAll().stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
