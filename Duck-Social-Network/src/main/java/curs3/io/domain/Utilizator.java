package curs3.io.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {
    private String username;
    private String email;
    private String password;
    private List<Utilizator> friends = new ArrayList<>();

    public Utilizator(Long id, String username, String email,  String password) {
        setId(id);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Utilizator(String username, String email,  String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String lastName) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    public void addFriend(Utilizator friend) {
        this.friends.add(friend);
    }

    public void removeFriend(Utilizator friend) {
        this.friends.remove(friend);
    }

    public boolean isFriend(Utilizator friend) {
        return this.friends.contains(friend);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + username + '\'' +
                ", lastName='" + email + '\'' +
                ", friends=" + friends +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getUsername().equals(that.getUsername()) &&
                getEmail().equals(that.getEmail()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getFriends());
    }
}