package curs3.io.domain;

import java.util.List;


public class SwimmingCard extends Card<Duck>{

    public SwimmingCard(Long id, String numeCard) {
        super(id, numeCard);
    }

    @Override
    public void addDuck(Utilizator d) {
        getMembrii().add((Duck) d);
    }

    @Override
    public void removeDuck(Utilizator d) {
        getMembrii().remove((Duck) d);
    }

    @Override
    public String toString() {
        return "SwimmingCard{ " +
                "id: " + this.getId() +
                ", nume: " + this.getNumeCard() + ", membrii: " + this.getMembrii() + " } ";
    }
}
