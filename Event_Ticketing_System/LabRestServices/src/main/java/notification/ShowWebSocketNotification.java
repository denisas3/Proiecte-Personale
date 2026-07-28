package notification;

import org.example.lab03.domain.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import websockets.ShowWebSocketHandler;


import java.util.List;

@Component
public class ShowWebSocketNotification implements ShowNotificationService {
    @Autowired
    private ShowWebSocketHandler webSocketHandler;

    public ShowWebSocketNotification() {
        System.out.println("creating ShowWebSocketNotification");
    }

    @Override
    public void showsUpdated(Show[] shows)
    {
        webSocketHandler.sendShowsAll(shows);
    }
}
