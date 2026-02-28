package curs3.io.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Duck extends Utilizator{
    private Double speed;
    private Double resistance;
    private DuckType type;
    private Long duckID;

    public Duck(Long duckID, Long userID, String username, String email, String password,
                Double speed, Double resistance, DuckType type) {

        super(userID, username, email, password);
        this.duckID = duckID;
        this.speed = speed;
        this.resistance = resistance;
        this.type = type;
    }

    public Duck(String username, String email, String password,
                Double speed, Double resistance, DuckType type) {

        super(username, email, password);
        this.speed = speed;
        this.resistance = resistance;
        this.type = type;
    }

    public Long getDuckID() { return duckID; }
    public void setDuckID(Long id) { this.duckID = id; }

//    public Duck(Long id,String username, String email,  String pasword, Double speed, Double resistance,  String type) {
//        super(id, username, email,pasword);
//        this.speed = speed;
//        this.resistance = resistance;
//        this.type = type;
//    }
//
//    public Duck(String username, String email,  String pasword, Double speed, Double resistance,  String type) {
//        super( username, email,pasword);
//        this.speed = speed;
//        this.resistance = resistance;
//        this.type = type;
//    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getResistance() {
        return resistance;
    }

    public void setResistance(Double resistance) {
        this.resistance = resistance;
    }

    public DuckType getType() {
        return type;
    }

    public void setType(DuckType type) {
        this.type = type;
    }

    @Override
    public String toString() {

        String friendIDsString = "";

        if (super.getFriends() != null && !super.getFriends().isEmpty()) {

            List<String> ids = super.getFriends().stream()
                    .map(u -> String.valueOf(u.getId()))
                    .toList();

            friendIDsString = String.join(", ", ids);
        }

        return "Duck{ " +
                "id: " + this.getId() +
                ", username: " + this.getUsername() +
                ", email: " + this.getEmail() +
                ", pasword: " + this.getPassword() +
                ", viteza: " + this.getSpeed() +
                ", resistance: " + this.getResistance() +
                ", type: " + this.getType() +
                ", id urile ptietenilor: " + friendIDsString +
                "}\n";
    }
}
