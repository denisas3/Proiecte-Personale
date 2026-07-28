//package org.example.lab03.domain;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotNull;
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//public class Employee{
//    private Long id;
//    private String username;
//    private String password;
//    private String name;
//
//    public Employee() {}
//
//    public Employee(String username, String password, String name) {
//        this.username = username;
//        this.password = password;
//        this.name = name;
//    }
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//    @NotNull
//    public String getUsername() {return username;}
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    @NotNull
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    @NotNull
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    @Override
//    public String toString() {
//        return "Employee [ id=" + id +", username=" + username + ", password=" + password + ", name=" + name + "]";
//    }
//
//}

package org.example.lab03.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@jakarta.persistence.Entity
@Table(name = "employees")
public class Employee extends org.example.lab03.domain.Entity<Long> {

    private String username;
    private String password;
    private String name;

    public Employee() {
    }

    public Employee(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    @Override
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee [ id=" + getId() +
                ", username=" + username +
                ", password=" + password +
                ", name=" + name + "]";
    }
}
