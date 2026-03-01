package org.example.restaurante.domain;

import java.time.LocalDateTime;
import java.util.List;

public class User extends Entity<Integer>{
    private String username;
    private Float buget;

    public User(String username, Float buget) {
        this.username = username;
        this.buget = buget;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Float getBuget() {
        return buget;
    }
    public void setBuget(Float buget) {
        this.buget = buget;
    }

}
