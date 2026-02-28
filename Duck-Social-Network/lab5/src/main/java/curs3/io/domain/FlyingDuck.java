package curs3.io.domain;

public class FlyingDuck extends Duck implements Zburator{
    public FlyingDuck(Long id,Long userID, String username, String email, String pasword, Double speed, Double resistance, DuckType type) {
        super(id,userID, username, email, pasword, speed, resistance, type);
    }

    public FlyingDuck( String username, String email, String pasword, Double speed, Double resistance, DuckType type) {
        super(username, email, pasword, speed, resistance, type);
    }

    @Override
    public void zboara() {

    }
}
