package org.example.lab03.services;

import org.example.lab03.Show;
import org.example.lab03.repository.ShowRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowService implements IShowService{
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public List<Show> getAllShows() {
        List<Show> result = new ArrayList<Show>();
        for (Show show : showRepository.findAll()){
            result.add(show);
        }
        return result;
    }

    @Override
    public List<Show> searchShowsByDate(LocalDateTime date) {

        return showRepository.findByDate(date);
    }

    @Override
    public Show findShow(Integer id) {
        return showRepository.findOne(id);
    }
}
