package dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String name;

    public EmployeeDTO(Long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void getPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "EmployeeDTO[username: " + this.username + ", password: " + this.password + ", name: " + this.name + "]";
    }
}
