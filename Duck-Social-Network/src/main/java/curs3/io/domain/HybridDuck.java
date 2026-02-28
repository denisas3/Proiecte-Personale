package curs3.io.domain;

public class HybridDuck extends Duck implements Zburator, Inotator {

    public HybridDuck(Long duckID, Long userID, String username, String email, String password,
                      Double speed, Double resistance, DuckType type) {
        super(duckID, userID, username, email, password, speed, resistance, type);
    }

    public HybridDuck(String username, String email, String password,
                      Double speed, Double resistance, DuckType type) {
        super(username, email, password, speed, resistance, type);
    }

    @Override
    public void zboara() {
        // comportament de zbor pentru o rață hibrid
    }

    @Override
    public void inoata() {
        // comportament de înot pentru o rață hibrid
    }
}
