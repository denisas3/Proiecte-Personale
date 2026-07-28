package org.example.lab03.services;

import org.example.lab03.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface IShowService {
    List<Show> getAllShows();
    List<Show> searchShowsByDate(LocalDateTime date);
    Show findShow(Integer id);
}
