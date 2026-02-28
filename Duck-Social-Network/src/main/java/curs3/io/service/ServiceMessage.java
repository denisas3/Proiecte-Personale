package curs3.io.service;

import curs3.io.domain.Message;
import curs3.io.domain.Utilizator;
import curs3.io.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;

public class ServiceMessage extends Observable {

    private final MessageRepository msgRepo;

    public ServiceMessage(MessageRepository repo) {
        this.msgRepo = repo;
    }

    public void sendMessage(Utilizator from, Utilizator to, String text, Message reply) {

        Message m = new Message(
                null,
                from,
                to,
                text,
                LocalDateTime.now(),
                reply
        );

        msgRepo.save(m);

        setChanged();
        notifyObservers(m);
    }

    public List<Message> getChat(Utilizator u1, Utilizator u2) {
        return msgRepo.getChat(u1.getId(), u2.getId());
    }

    public Message findMessage(Long id) {
        return msgRepo.findById(id);
    }
}
