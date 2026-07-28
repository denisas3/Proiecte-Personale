package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan({
//        "services.rest",
//        "org.example.lab03.repository"
//})
@SpringBootApplication(scanBasePackages = {
        "start",
        "services.rest",
        "websockets",
        "notification",
        "org.example.lab03"
})

public class StartRestServices {
    public static void main(String[] args) {

        SpringApplication.run(StartRestServices.class, args);
    }
}
