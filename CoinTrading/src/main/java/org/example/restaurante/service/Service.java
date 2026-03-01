package org.example.restaurante.service;

import org.example.restaurante.domain.Coins;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.Transaction;
import org.example.restaurante.domain.User;
import org.example.restaurante.repository.CoinRepository;
import org.example.restaurante.repository.TransactionRepository;
import org.example.restaurante.repository.UserRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.util.List;
import java.util.Optional;

import static org.example.restaurante.utils.events.ChangeEventType.UPDATE;

public class Service implements Observable<EntityChangeEvent> {
    UserRepository userRepository;
    CoinRepository coinsRepository;
    TransactionRepository transactionRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(UserRepository userRepository, CoinRepository coinsRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.coinsRepository = coinsRepository;
        this.transactionRepository = transactionRepository;
        observers = new java.util.ArrayList<>();
    }

    @Override
    public void addObserver(Observer<EntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EntityChangeEvent event) {
        observers.forEach(o -> o.update(event));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<Coins> getCoins() {
        return coinsRepository.findAll();
    }

    public void buyCoin(User user, Coins coin) {
        float cost = coin.getPret();

        if (user.getBuget() < cost) {
            throw new IllegalArgumentException("Buget insuficient!");
        }

        user.setBuget(user.getBuget() - cost);

        userRepository.update(user);

        transactionRepository.save(new Transaction(
                user.getId(),
                coin.getId(),
                Status.BUY, // sau SELL
                (double) coin.getPret(),
                java.time.LocalDateTime.now()
        ));

        notifyObservers(new EntityChangeEvent(UPDATE,coin));
    }

    public void sellCoin(User user, Coins coin) {
        float gain = coin.getPret();

        user.setBuget(user.getBuget() + gain);

        userRepository.update(user);

        transactionRepository.save(new Transaction(
                user.getId(),
                coin.getId(),
                Status.SELL,
                (double) coin.getPret(),
                java.time.LocalDateTime.now()
        ));

        notifyObservers(new EntityChangeEvent(UPDATE,coin));
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public Optional<Coins> findCoinById(Integer id) {
        return coinsRepository.findOne(id);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll().stream()
                .sorted(java.util.Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
    }

}
