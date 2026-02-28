package curs3.io.domain;

import curs3.io.domain.Message;
import curs3.io.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private Message repliedMessage;

    public ReplyMessage(Long id, Utilizator from, Utilizator to,
                        String message, LocalDateTime data, Message repliedMessage) {
        super(id, from, to, message, data, repliedMessage);
        this.repliedMessage = repliedMessage;
    }

    public Message getRepliedMessage() {
        return repliedMessage;
    }

    public void setRepliedMessage(Message repliedMessage) {
        this.repliedMessage = repliedMessage;
    }
}

