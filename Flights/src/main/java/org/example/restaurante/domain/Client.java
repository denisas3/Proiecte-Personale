package org.example.restaurante.domain;

public class Client extends Entity<Integer>{
    private String username;
    private String nume;

    public Client(String username, String nume) {
        this.username = username;
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
}
