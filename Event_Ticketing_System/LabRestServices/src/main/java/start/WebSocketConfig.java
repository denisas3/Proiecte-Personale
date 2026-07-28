package start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import websockets.ShowWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Bean
    public ShowWebSocketHandler createShowWebSocketHandler(){
        return new ShowWebSocketHandler();
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(createShowWebSocketHandler(), "/showsws").setAllowedOrigins("*");
        System.out.println("Registered WebSocket Handler for shows");
    }
}
