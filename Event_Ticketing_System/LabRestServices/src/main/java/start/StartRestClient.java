package start;

import org.example.lab03.domain.Show;
import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestClientException;
import rest.client.ShowRestClient;
import services.rest.ServiceException;

import java.time.LocalDateTime;

public class StartRestClient {

    private static final ShowRestClient showRestClient = new ShowRestClient();
    public static void main(String[] args) {
        Show showT = new Show("Delia", LocalDateTime.of(2026, 3, 26, 19, 30), "Cluj-Napoca", 160, 40);
        try {
            System.out.println("\nCreating show..." + showT);
            show(() -> System.out.println(showRestClient.create(showT)));
            System.out.println("\nPrinting all shows ...");
            show(() -> {
                        Show[] res = showRestClient.getAll();
                        for (Show s : res) {
                            System.out.println(s.toString());
                        }
                    }
            );
        } catch (RestClientException ex) {
            System.out.println("Exception ... " + ex.getMessage());
        }

        System.out.println("\nInfo for user with id=1");
        show(() -> System.out.println(showRestClient.getShowById(1)));

        Show createdShow = showRestClient.create(showT);
        System.out.println("\nDeleting user with id=12");
        show(() -> showRestClient.delete(12));

        System.out.println("\nPrinting all shows ...");
        show(() -> {
                    Show[] res = showRestClient.getAll();
                    for (Show s : res) {
                        System.out.println(s.toString());
                    }
                }
        );

        System.out.println("\nInfo for user with nameArtist=" + createdShow.getArtistName());
        show(() -> {
            Show[] shows = showRestClient.getShowByArtist(createdShow.getArtistName());
            for (Show s : shows) {
                System.out.println(s);
            }
        });

        Show showT2 = new Show(
                "Smiley",
                LocalDateTime.of(2026, 6, 10, 20, 0),
                "Bucuresti",
                500,
                50
        );

        showT2.setId(createdShow.getId());

        System.out.println("\nUpdating show with id=" + showT2.getId());

        show(() -> showRestClient.update(showT2));

        System.out.println("Show updated!");

        System.out.println("\nShow after update:");
        show(() -> System.out.println(showRestClient.getShowById(showT2.getId())));
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
