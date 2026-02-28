
package curs3.io.ui;

import curs3.io.domain.*;
import curs3.io.service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class Ui {
    private final Service service;
    private final ServiceCard serviceCard;
    private final ServiceEvent serviceEvent;
    private final ServiceDuck serviceDuck;
    private final ServicePersoana servicePersoana;
    private final ServiceUser userService;
    private final ServiceFriendship serviceFriendship;

    public Ui(Service service, ServicePersoana servicePersoana, ServiceDuck serviceDuck,
              ServiceCard serviceCard, ServiceEvent serviceEvent, ServiceUser userService, ServiceFriendship serviceFriendship) {
        this.service = service;
        this.servicePersoana = servicePersoana;
        this.serviceDuck = serviceDuck;
        this.serviceCard = serviceCard;
        this.serviceEvent = serviceEvent;
        this.userService = userService;
        this.serviceFriendship = serviceFriendship;
    }

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        while (true) {
            try {
                printMainMenu();
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1 -> personsMenu();
                    case 2 -> ducksMenu();
                    case 3 -> friendshipMenu();
                    case 4 -> cardMenu();
                    case 5 -> eventMenu();
                    case 6 -> usersMenu();
                    case 0 -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= MAIN MENU ==================

    private void printMainMenu() {
        System.out.println("\n=== MENIU PRINCIPAL ===");
        System.out.println("1. Persoane");
        System.out.println("2. Rata");
        System.out.println("3. Prietenii");
        System.out.println("4. Carduri");
        System.out.println("5. Evenimente");
        System.out.println("6. User");
        System.out.println("0. Iesire");
        System.out.print("Alege: ");
    }

    // ================= PERSOANE ==================

    private void personsMenu() throws Exception {
        while (true) {
            System.out.println("\n--- MENIU PERSOANE ---");
            System.out.println("1. Adauga persoana");
            System.out.println("2. Sterge persoana");
            System.out.println("3. Afiseaza persoane");
            System.out.println("4. Modifica persoane");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> addPerson();
                case 2 -> removePerson();
                case 3 -> showPersons();
                case 4 -> updatePerson();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void addPerson() {
        try {
            System.out.print("Username: ");
            String u = br.readLine();
            System.out.print("Email: ");
            String e = br.readLine();
            System.out.print("Password: ");
            String p = br.readLine();
            System.out.print("Lastname: ");
            String ln = br.readLine();
            System.out.print("Firstname: ");
            String fn = br.readLine();
            System.out.print("Job title: ");
            String jt = br.readLine();
            System.out.print("Birthdate (yyyy-mm-dd): ");
            LocalDate bd = LocalDate.parse(br.readLine());
            System.out.print("Empathy level: ");
            int lvl = Integer.parseInt(br.readLine());
            Persoana pers = new Persoana(u, e, p, ln, fn, jt, bd, lvl);
            servicePersoana.addPersoana(pers);

            System.out.println("Persoana adaugata!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void removePerson() {
        try {
            System.out.print("ID persoana: ");
            long id = Long.parseLong(br.readLine());
            servicePersoana.removePersoana(id);
            System.out.println("Persoana stearsa!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void showPersons() {
        for (Persoana p : servicePersoana.findAll())
            System.out.println(p);
    }

    private void updatePerson() {
        try {
            System.out.print("ID: ");
            long id = Long.parseLong(br.readLine());
            System.out.print("Username: ");
            String u = br.readLine();
            System.out.print("Email: ");
            String e = br.readLine();
            System.out.print("Password: ");
            String p = br.readLine();
            System.out.print("Lastname: ");
            String ln = br.readLine();
            System.out.print("Firstname: ");
            String fn = br.readLine();
            System.out.print("Job title: ");
            String jt = br.readLine();
            System.out.print("Birthdate (yyyy-mm-dd): ");
            LocalDate bd = LocalDate.parse(br.readLine());
            System.out.print("Empathy level: ");
            int lvl = Integer.parseInt(br.readLine());

            Persoana pers = new Persoana(u, e, p, ln, fn, jt, bd, lvl);
            servicePersoana.updatePersoana(pers);

            System.out.println("Persoana modificata!");
        }catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    // ================= DUCKS ==================

    private void ducksMenu() throws Exception {
        while (true) {
            System.out.println("\n--- MENIU RATE ---");
            System.out.println("1. Adauga rata");
            System.out.println("2. Sterge rata");
            System.out.println("3. Afiseaza rate");
            System.out.println("4. Modifica rata");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> addDuck();
                case 2 -> removeDuck();
                case 3 -> showDucks();
                case 4 -> updateDuck();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void addDuck() {
        try {
            System.out.print("Username: ");
            String u = br.readLine();
            System.out.print("Email: ");
            String e = br.readLine();
            System.out.print("Password: ");
            String p = br.readLine();
            System.out.print("Speed: ");
            double s = Double.parseDouble(br.readLine());
            System.out.print("Resistance: ");
            double r = Double.parseDouble(br.readLine());
            System.out.print("Type (FLYING / SWIMMING / FLYING_AND_SWIMMING): ");
            DuckType type = DuckType.valueOf(br.readLine().trim().toUpperCase());


            Duck duck = switch (type) {
                case FLYING -> new FlyingDuck(u, e, p, s, r, DuckType.FLYING);
                case SWIMMING -> new SwimmingDuck(u, e, p, s, r, DuckType.SWIMMING);
                case FLYING_AND_SWIMMING -> new HybridDuck(u, e, p, s, r, DuckType.FLYING_AND_SWIMMING);
            };

            serviceDuck.addDuck(duck);

            System.out.println("Rata adaugata!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void removeDuck() {
        try {
            System.out.print("ID rata: ");
            long id = Long.parseLong(br.readLine());
            serviceDuck.removeDuck(id);
            System.out.println("Rata stearsa!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void showDucks() {
        for (Duck d : serviceDuck.findAll())
            System.out.println(d);
    }

    private void updateDuck() {
        try {
            System.out.print("ID: ");
            long id = Long.parseLong(br.readLine());

            Duck oldDuck = serviceDuck.findById(id);

            System.out.print("Username: ");
            String u = br.readLine();
            System.out.print("Email: ");
            String e = br.readLine();
            System.out.print("Password: ");
            String p = br.readLine();
            System.out.print("Speed: ");
            double s = Double.parseDouble(br.readLine());
            System.out.print("Resistance: ");
            double r = Double.parseDouble(br.readLine());
            System.out.print("Type (FLYING / SWIMMING / FLYING_AND_SWIMMING): ");
            DuckType type = DuckType.valueOf(br.readLine().trim().toUpperCase());

            Duck duck = switch (type) {
                case FLYING -> new FlyingDuck(oldDuck.getDuckID(), oldDuck.getId(), u, e, p, s, r, DuckType.FLYING);
                case SWIMMING -> new SwimmingDuck(oldDuck.getDuckID(), oldDuck.getId(), u, e, p, s, r, DuckType.SWIMMING);
                case FLYING_AND_SWIMMING -> new HybridDuck(oldDuck.getDuckID(), oldDuck.getId(), u, e, p, s, r, DuckType.FLYING_AND_SWIMMING);
            };
            serviceDuck.updateDuck(duck);

            System.out.println("Rata modificata!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    // ================= USERS ==================

    private void usersMenu() throws Exception {
        while (true) {
            System.out.println("\n--- MENIU PERSOANE ---");
            System.out.println("1. Sterge persoana");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> removeUser();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void removeUser() {
        try {
            System.out.print("ID user: ");
            long id = Long.parseLong(br.readLine());
            userService.deleteUser(id);
            System.out.println("User sters!");
        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    // ================= PRIETENII ==================

    private void friendshipMenu() throws Exception {
        while (true) {
            System.out.println("\n--- PRIETENII ---");
            System.out.println("1. Adauga prietenie");
            System.out.println("2. Sterge prietenie");
            System.out.println("3. Afiseaza numar comunitati");
            System.out.println("4. Afiseaza prietenii unui user");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> addFriendship();
                case 2 -> removeFriendship();
                case 3 -> System.out.println("Comunitati: " + serviceFriendship.getNumarComunitati());
                case 4 -> showFriends();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void addFriendship() {
        try {
            System.out.print("ID1: ");
            long id1 = Long.parseLong(br.readLine());
            System.out.print("ID2: ");
            long id2 = Long.parseLong(br.readLine());

            serviceFriendship.addFriend(id1, id2);

            System.out.println("Prietenie adaugata!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void removeFriendship() {
        try {
            System.out.print("ID1: ");
            long id1 = Long.parseLong(br.readLine());
            System.out.print("ID2: ");
            long id2 = Long.parseLong(br.readLine());

            serviceFriendship.removeFriend(id1, id2);

            System.out.println("Prietenie stearsa!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void showFriends() {
        try {
            System.out.print("ID user: ");
            long id = Long.parseLong(br.readLine());

            var friends = serviceFriendship.getFriendsOf(id);

            if (friends.isEmpty()) {
                System.out.println("Acest user nu are prieteni.");
                return;
            }

            System.out.println("\nPrieteni:");
            for (var friend : friends) {
                System.out.println("{" + friend.getId() + " , " + friend.getUsername() + " , " + friend.getEmail()+ "}");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    // ================= CARDURI ==================

    private void cardMenu() throws Exception {
        while (true) {
            System.out.println("\n--- CARDURI ---");
            System.out.println("1. Adauga card");
            System.out.println("2. Sterge card");
            System.out.println("3. Afiseaza carduri");
            System.out.println("4. Adauga rata in card");
            System.out.println("5. Sterge rata din card");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> addCardBD();
                case 2 -> removeCardBD();
                case 3 -> showCardsBD();
                case 4 -> addDuckToCardBD();
                case 5 -> removeDuckFromCardBD();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void addCardBD() {
        try {
            System.out.print("Tip card (FLYING / SWIMMING / FLYING_AND_SWIMMING): ");
            DuckType type = DuckType.valueOf(br.readLine().trim().toUpperCase());

            Card<Duck> card = switch (type) {
                case FLYING -> new FlyingCard(null, "FLYING");
                case SWIMMING -> new SwimmingCard(null, "SWIMMING");
                case FLYING_AND_SWIMMING -> null;
            };
            serviceCard.addCard(card);

            System.out.println("Card adaugat! ID generat = " + card.getId());

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }


    private void removeCardBD() {
        try {
            System.out.print("ID card: ");
            long id = Long.parseLong(br.readLine());

            serviceCard.removeCard(id);
            System.out.println("Card sters!");

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void showCardsBD() {
        try {
            var cards = serviceCard.getAllCards();

            if (cards.isEmpty()) {
                System.out.println("Nu exista carduri in baza de date.");
                return;
            }

            System.out.println("\n=== CARDURI ===");
            for (Card<?> c : cards) {
                System.out.println("Card #" + c.getId() + " | " + c.getNumeCard());

                var membrii = serviceCard.getDucksForCard(c.getId());

                if (membrii.isEmpty()) {
                    System.out.println("   (Fara rate)");
                } else {
                    System.out.println("   Rate:");
                    for (Duck d : membrii)
                        System.out.println("     - Duck #" + d.getDuckID() + " (" + d.getType().name()  + ")");
                }
            }

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void addDuckToCardBD() {
        try {
            System.out.print("ID card: ");
            long cardId = Long.parseLong(br.readLine());

            System.out.print("ID duck: ");
            long duckId = Long.parseLong(br.readLine());

            Duck d = serviceDuck.findById(duckId);

            serviceCard.addDuckToCard(cardId, d);

            System.out.println("Rata adaugata in card!");

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    private void removeDuckFromCardBD() {
        try {
            System.out.print("ID card: ");
            long cardId = Long.parseLong(br.readLine());

            System.out.print("ID duck: ");
            long duckId = Long.parseLong(br.readLine());

            Duck d = serviceDuck.findById(duckId);

            serviceCard.removeDuckFromCard(cardId, d);

            System.out.println("Rata stearsa din card!");

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }

    // ================= EVENIMENTE ==================

    private void eventMenu() throws Exception {
        while (true) {
            System.out.println("\n--- EVENIMENTE ---");
            System.out.println("1. Adauga event");
            System.out.println("2. Sterge event");
            System.out.println("3. Afiseaza events");
            System.out.println("4. Adauga utilizator in event");
            System.out.println("5. Sterge utilizator din event");
            System.out.println("6. Afiseaza subscriberi pentru event");
            System.out.println("7. Selectie rate (din toti subscriberii)");
            System.out.println("0. Inapoi");
            System.out.print("Alege: ");

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> addEventBD();
                case 2 -> removeEventBD();
                case 3 -> showEventsBD();
                case 4 -> addSubscriberBD();
                case 5 -> removeSubscriberBD();
                case 6 -> showSubscribersBD();
                case 7 -> selectDucksBD();
                case 0 -> { return; }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }


    private void addEventBD() {
        try {
            System.out.print("Nume event: ");
            String name = br.readLine();

            Event e = new RaceEvent(null, name);
            serviceEvent.addEvent(e);

            System.out.println("Event creat cu ID = " + e.getId());

        } catch (Exception ex) {
            System.out.println("Eroare: " + ex.getMessage());
        }
    }


    private void removeEventBD() {
        try {
            System.out.print("ID event: ");
            long id = Long.parseLong(br.readLine());

            serviceEvent.removeEvent(id);

            System.out.println("Event sters!");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void showEventsBD() {
        try {
            var events = serviceEvent.getAllEvents();

            if (events.isEmpty()) {
                System.out.println("Nu exista evenimente.");
                return;
            }

            System.out.println("\n=== EVENTURI ===");
            for (Event e : events) {
                System.out.println("Event #" + e.getId() + " | " + e.getNumeEvent());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void addSubscriberBD() {
        try {
            System.out.print("ID event: ");
            long eventId = Long.parseLong(br.readLine());

            System.out.print("ID user: ");
            long userId = Long.parseLong(br.readLine());

            serviceEvent.addSubscriber(eventId, userId);

            System.out.println("Utilizator adaugat la event!");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void removeSubscriberBD() {
        try {
            System.out.print("ID event: ");
            long eventId = Long.parseLong(br.readLine());

            System.out.print("ID user: ");
            long userId = Long.parseLong(br.readLine());

            serviceEvent.removeSubscriber(eventId, userId);

            System.out.println("Utilizator sters din event!");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void showSubscribersBD() {
        try {
            System.out.print("ID event: ");
            long eventId = Long.parseLong(br.readLine());

            var subs = serviceEvent.getSubscribers(eventId);

            if (subs.isEmpty()) {
                System.out.println("Eventul nu are subscriberi.");
                return;
            }

            System.out.println("\n--- SUBSCRIBERI ---");
            for (Utilizator u : subs) {
                System.out.println(u);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void selectDucksBD() {
        try {
            System.out.print("Numar rate: ");
            long nr = Long.parseLong(br.readLine());

            System.out.println(serviceEvent.selectTopDucks(nr));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}


