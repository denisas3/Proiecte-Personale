package curs3.io.domain;

import curs3.io.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private Long id;
    private Utilizator from;
    private Utilizator to;
    private String message;
    private LocalDateTime data;
    private Message reply;

    public Message(Long id, Utilizator from, Utilizator to, String message, LocalDateTime data, Message reply) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    public Message getReply() {
        return reply;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public void setTo(Utilizator to) {
        this.to = to;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Utilizator getFrom() {
        return from;
    }

    public Utilizator getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getData() {
        return data;
    }
}
