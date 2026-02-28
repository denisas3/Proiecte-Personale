package curs3.io.service;

import curs3.io.domain.Card;
import curs3.io.domain.Duck;
import curs3.io.repository.CardDuckRepository;
import curs3.io.repository.CardRepository;

import java.util.List;

public class ServiceCard {

    private final CardRepository cardRepo;
    private final CardDuckRepository cardDuckRepo;

    public ServiceCard(CardRepository cardRepo, CardDuckRepository cardDuckRepo) {
        this.cardRepo = cardRepo;
        this.cardDuckRepo = cardDuckRepo;
    }

    public void addCard(Card<?> card) {
        cardRepo.save(card);
    }

    public void removeCard(Long id) {
        cardRepo.delete(id);
        cardDuckRepo.deleteAllDucksFromCard(id);
    }

    public List<Card<?>> getAllCards() {
        return cardRepo.findAll();
    }

    public void addDuckToCard(Long cardID, Duck duck) {
        Card<?> card = cardRepo.findById(cardID);
        if (card == null) {
            throw new RuntimeException("Cardul cu ID " + cardID + " NU exista!");
        }

        if (!card.getNumeCard().equalsIgnoreCase(duck.getType().name())) {
            throw new RuntimeException(
                    "NU poti adauga o rata de tip " + duck.getType() +
                            " intr un card de tip " + card.getNumeCard() + "!"
            );
        }

        cardDuckRepo.addDuckToCard(cardID, duck.getDuckID());
    }

    public void removeDuckFromCard(Long cardID, Duck duck) {
        cardDuckRepo.removeDuckFromCard(cardID, duck.getDuckID());
    }

    public List<Duck> getDucksForCard(Long cardID) {
        return cardDuckRepo.findDucksForCard(cardID);
    }
}
