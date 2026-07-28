package org.example.lab03.repository;

import org.example.lab03.domain.Show;

import java.time.LocalDateTime;
import java.util.List;


public interface ShowRepository extends Repository<Long, Show> {

    List<Show> findByDate(LocalDateTime date);
    List<Show> findByArtist(String artist);
    Show delete(Long integer);
}
