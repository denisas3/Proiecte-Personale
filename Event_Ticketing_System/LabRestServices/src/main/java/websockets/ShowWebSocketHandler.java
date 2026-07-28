package websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.lab03.domain.Show;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ShowWebSocketHandler extends TextWebSocketHandler{
    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public ShowWebSocketHandler(){
        System.out.println("Constructing new ShowWebSocketHandler");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        System.out.println("[Websocket] New TextMessage: " +  message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        System.out.println("New websocket session established " + session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)  {
        System.out.println("Removing websocket session"+session.getId());
        sessions.remove(session);
    }

    public void sendShowsAll(Show[] shows){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try{
            String result = objectMapper.writeValueAsString(shows);
            System.out.println("Websockets sending data "+result);
            for(WebSocketSession session : sessions){
                try{
                    session.sendMessage(new TextMessage(result));
                }catch(IOException e){
                    System.out.println("error sending message to websocket"+e);
                }
            }
        }catch(JsonProcessingException e){
            System.out.println("error writing object"+e);
        }
    }
}
