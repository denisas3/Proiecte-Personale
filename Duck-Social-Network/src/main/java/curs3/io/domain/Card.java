package curs3.io.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Card<T extends Duck> extends Entity<Long>{
    private String numeCard;
    private List<Duck> membrii;

    public Card( Long id, String numeCard) {
        this.id = id;
        this.numeCard = numeCard;
        this.membrii = new ArrayList<Duck>();
    }

    public String getNumeCard() {
        return numeCard;
    }

    public void setNumeCard(String numeCard) {
        this.numeCard = numeCard;
    }

    public List<Duck> getMembrii() {
        return membrii;
    }

    public double getPerformatntaMedieViteza() throws Exception
    {
        double sum_speer = 0.0;
        for(Duck d : membrii)
            sum_speer += d.getSpeed();
        if(membrii.size()>0)
        {
            return (sum_speer/membrii.size());
        }
        else throw new Exception("Nu exista membrii in card.");

    }

    public double getPerformatntaMedieRezistenta() throws Exception {
        double sum_resistance = 0.0;
        for(Duck d : membrii)
            sum_resistance += d.getResistance();

        if(membrii.size()>0)
        {
            return (sum_resistance/membrii.size());
        }
        else throw new Exception("Nu exista membrii in card.");
    }

    public abstract void addDuck(Utilizator d);
    public abstract void removeDuck(Utilizator d);
    public abstract String toString();

}

