package services.rest;


import notification.ShowNotificationService;
import notification.ShowWebSocketNotification;
import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.repository.RepositoryException;
import org.example.lab03.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/shows")
public class ShowRestController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowNotificationService showNotificationService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Show> getAll() {
        System.out.println("Get all shows ...");
        return showRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShowById(@PathVariable long id) {
        System.out.println("Get show by id " + id);
        Show show = showRepository.findOne(id);
        if (show == null)
            return new ResponseEntity<String>("Show not found ...",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Show>(show, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Show create(@RequestBody Show show) {
        System.out.println("Create show " + show);
        showRepository.save(show);
        notifyChanges();
        return show;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Show update(@PathVariable Long id,@RequestBody Show show) {
        System.out.println("Update show " + show);
        show.setId(id);
        showRepository.update(show);
        notifyChanges();
        return show;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        System.out.println("Delete show ..." + id);
        try{
            showRepository.delete(id);
            notifyChanges();
            return new ResponseEntity<Show>(HttpStatus.OK);
        }catch(Exception ex){
            System.out.println("Delete show exception ..." );
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/artist/{artist}", method = RequestMethod.GET)
    public Iterable<Show> getShowsByArtist(@PathVariable String artist) {
        System.out.println("Get shows by artist " + artist);
        return showRepository.findByArtist(artist);
    }

//    private void notifyChanges() {
//        showNotificationService.showsUpdated(showRepository.findAll());
//    }

    private void notifyChanges() {
        List<Show> showsList = new ArrayList<>();
        showRepository.findAll().forEach(showsList::add);

        showNotificationService.showsUpdated(
                showsList.toArray(new Show[0])
        );
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }

}
