package curs3.io.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Persoana extends Utilizator{
    private String lastName;
    private String firstName;
    private String jobTitle;
    private LocalDate birthDate;
    private Integer empathyLevel;
    private Long userID;
    private Long persoanaID;

    public Persoana( String username, String email,  String pasword, String lastName,
                    String firstName, String jobTitle, LocalDate birthDate, Integer empathyLevel) {
        super( username, email, pasword);
        this.lastName = lastName;
        this.firstName = firstName;
        this.jobTitle = jobTitle;
        this.birthDate = birthDate;
        this.empathyLevel = empathyLevel;
    }

    public Persoana(Long persoanaID, Long userID, String username, String email,  String pasword, String lastName,
                    String firstName, String jobTitle, LocalDate birthDate, Integer empathyLevel) {
        super(userID, username, email, pasword);
        this.persoanaID = persoanaID;
        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.jobTitle = jobTitle;
        this.birthDate = birthDate;
        this.empathyLevel = empathyLevel;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getEmpathyLevel() {
        return empathyLevel;
    }

    public void setEmpathyLevel(Integer empathyLevel) {
        this.empathyLevel = empathyLevel;
    }

    public Long getUserID() { return userID; }
    public void setUserID(Long id) { this.userID = id; }


    @Override
    public String toString() {

        String friendIDsString = "";

        if (super.getFriends() != null && !super.getFriends().isEmpty()) {
            friendIDsString = super.getFriends().stream()
                    .map(u -> String.valueOf(u.getId()))
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
        }

        return "Persoana{ id: " + getId() +
                ", username: " + getUsername() +
                ", email: " + getEmail() +
                ", password: " + getPassword() +
                ", lastname: " + lastName +
                ", firstname: " + firstName +
                ", jobtitle: " + jobTitle +
                ", birthdate: " + birthDate +
                ", empathyLevel: " + empathyLevel +
                ", prieteni: [" + friendIDsString + "] }";
    }

}
