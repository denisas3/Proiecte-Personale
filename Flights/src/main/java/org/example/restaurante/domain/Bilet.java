package org.example.restaurante.domain;

import java.time.LocalDateTime;

public class Bilet extends Entity<Integer>{
    private String username;
    private Integer id_zbor;
    private LocalDateTime data_cumparare;

    public Bilet(String username,Integer id_zbor,LocalDateTime data_cumparare){
        this.username = username;
        this.id_zbor = id_zbor;
        this.data_cumparare = data_cumparare;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId_zbor() {
        return id_zbor;
    }
    public void setId_zbor(Integer id_zbor) {
        this.id_zbor = id_zbor;
    }

    public LocalDateTime getData_cumparare() {
        return data_cumparare;
    }
    public void setData_cumparare(LocalDateTime data_cumparare) {
        this.data_cumparare = data_cumparare;
    }

}
