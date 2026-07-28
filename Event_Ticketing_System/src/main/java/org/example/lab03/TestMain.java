//package org.example.lab03;
//
//import org.example.lab03.domain.Employee;
//import org.example.lab03.domain.Show;
//import org.example.lab03.domain.Ticket;
//import org.example.lab03.repository.EmployeeDbRepository;
//import org.example.lab03.repository.ShowDbRepository;
//import org.example.lab03.repository.TicketDbRepository;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Properties;
//
//public class TestMain {
//    public static void main(String[] args) {
//        Properties props = new Properties();
//
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Eroare la citirea fisierului de proprietati: " + e.getMessage());
//            return;
//        }
//
//        EmployeeDbRepository employeeRepo = new EmployeeDbRepository(props);
//        ShowDbRepository showRepo = new ShowDbRepository(props);
//        TicketDbRepository ticketRepo = new TicketDbRepository(props);
//
//        // =========================
//        // TEST EMPLOYEE
//        // =========================
//        System.out.println("----- TEST SAVE EMPLOYEE -----");
//        Employee emp = new Employee("user_test", "pass123", "Ion Pop");
//        Employee saveResult = employeeRepo.save(emp);
//
//        if (saveResult == null) {
//            System.out.println("Employee adaugat cu succes.");
//        } else {
//            System.out.println("Eroare la adaugarea employee.");
//        }
//
//        System.out.println("\n----- TEST FIND ALL EMPLOYEES -----");
//        for (Employee e : employeeRepo.findAll()) {
//            System.out.println(
//                    "ID: " + e.getId() +
//                            ", Username: " + e.getUsername() +
//                            ", Password: " + e.getPassword() +
//                            ", Name: " + e.getName()
//            );
//        }
//
//        System.out.println("\n----- TEST UPDATE EMPLOYEE -----");
//        Employee employeeToUpdate = employeeRepo.findByUsername("user_test");
//        if (employeeToUpdate != null) {
//            employeeToUpdate.setPassword("newpass456");
//            employeeToUpdate.setName("Ion Popescu");
//
//            Employee updateResult = employeeRepo.update(employeeToUpdate);
//            if (updateResult == null) {
//                System.out.println("Employee actualizat cu succes.");
//            } else {
//                System.out.println("Eroare la update employee.");
//            }
//        } else {
//            System.out.println("Nu s-a gasit employee pentru update.");
//        }
//
//        System.out.println("\n----- EMPLOYEES DUPA UPDATE -----");
//        for (Employee e : employeeRepo.findAll()) {
//            System.out.println(
//                    "ID: " + e.getId() +
//                            ", Username: " + e.getUsername() +
//                            ", Password: " + e.getPassword() +
//                            ", Name: " + e.getName()
//            );
//        }
//
//        // =========================
//        // TEST SHOW
//        // =========================
//        System.out.println("\n----- TEST SAVE SHOW -----");
//        Show show = new Show(
//                "Artist Test",
//                LocalDateTime.of(2026, 3, 20, 19, 0),
//                "Cluj",
//                100,
//                0
//        );
//
//        Show saveShowResult = showRepo.save(show);
//        if (saveShowResult == null) {
//            System.out.println("Show adaugat cu succes.");
//        } else {
//            System.out.println("Eroare la adaugarea show.");
//        }
//
//        System.out.println("\n----- TEST FIND ALL SHOWS -----");
//        for (Show s : showRepo.findAll()) {
//            System.out.println(
//                    "ID: " + s.getId() +
//                            ", Artist: " + s.getArtistName() +
//                            ", Date: " + s.getDate() +
//                            ", Location: " + s.getLocation() +
//                            ", Available: " + s.getAvailableSeats() +
//                            ", Sold: " + s.getSoldSeats()
//            );
//        }
//
//        System.out.println("\n----- TEST UPDATE SHOW -----");
//        Show showToUpdate = null;
//        for (Show s : showRepo.findAll()) {
//            if (s.getArtistName().equals("Artist Test")) {
//                showToUpdate = s;
//                break;
//            }
//        }
//
//        if (showToUpdate != null) {
//            showToUpdate.setLocation("Bucuresti");
//            showToUpdate.setAvailableSeats(80);
//            showToUpdate.setSoldSeats(20);
//
//            Show updateShowResult = showRepo.update(showToUpdate);
//            if (updateShowResult == null) {
//                System.out.println("Show actualizat cu succes.");
//            } else {
//                System.out.println("Eroare la update show.");
//            }
//        } else {
//            System.out.println("Nu s-a gasit show pentru update.");
//        }
//
//        System.out.println("\n----- SHOWS DUPA UPDATE -----");
//        for (Show s : showRepo.findAll()) {
//            System.out.println(
//                    "ID: " + s.getId() +
//                            ", Artist: " + s.getArtistName() +
//                            ", Date: " + s.getDate() +
//                            ", Location: " + s.getLocation() +
//                            ", Available: " + s.getAvailableSeats() +
//                            ", Sold: " + s.getSoldSeats()
//            );
//        }
//
//        // =========================
//        // TEST TICKET
//        // =========================
//        System.out.println("\n----- TEST SAVE TICKET -----");
//
//        Show showForTicket = null;
//        for (Show s : showRepo.findAll()) {
//            if (s.getArtistName().equals("Artist Test")) {
//                showForTicket = s;
//                break;
//            }
//        }
//
//        if (showForTicket != null) {
//            Ticket ticket = new Ticket(
//                    "Maria Pop",
//                    2,
//                    LocalDateTime.now(),
//                    showForTicket
//            );
//
//            Ticket saveTicketResult = ticketRepo.save(ticket);
//            if (saveTicketResult == null) {
//                System.out.println("Ticket adaugat cu succes.");
//            } else {
//                System.out.println("Eroare la adaugarea ticket.");
//            }
//        } else {
//            System.out.println("Nu exista show pentru ticket.");
//        }
//
//        System.out.println("\n----- TEST FIND ALL TICKETS -----");
//        for (Ticket t : ticketRepo.findAll()) {
//            System.out.println(
//                    "ID: " + t.getId() +
//                            ", Buyer: " + t.getBuyerName() +
//                            ", Seats: " + t.getSeatCount() +
//                            ", SoldAt: " + t.getSoldAt() +
//                            ", ShowID: " + t.getShow().getId() +
//                            ", ShowArtist: " + t.getShow().getArtistName()
//            );
//        }
//
//        System.out.println("\n----- TEST UPDATE TICKET -----");
//        Ticket ticketToUpdate = null;
//        for (Ticket t : ticketRepo.findAll()) {
//            if (t.getBuyerName().equals("Maria Pop")) {
//                ticketToUpdate = t;
//                break;
//            }
//        }
//
//        if (ticketToUpdate != null) {
//            ticketToUpdate.setBuyerName("Maria Ionescu");
//            ticketToUpdate.setSeatCount(3);
//
//            Ticket updateTicketResult = ticketRepo.update(ticketToUpdate);
//            if (updateTicketResult == null) {
//                System.out.println("Ticket actualizat cu succes.");
//            } else {
//                System.out.println("Eroare la update ticket.");
//            }
//        } else {
//            System.out.println("Nu s-a gasit ticket pentru update.");
//        }
//
//        System.out.println("\n----- TICKETS DUPA UPDATE -----");
//        for (Ticket t : ticketRepo.findAll()) {
//            System.out.println(
//                    "ID: " + t.getId() +
//                            ", Buyer: " + t.getBuyerName() +
//                            ", Seats: " + t.getSeatCount() +
//                            ", SoldAt: " + t.getSoldAt() +
//                            ", ShowID: " + t.getShow().getId() +
//                            ", ShowArtist: " + t.getShow().getArtistName()
//            );
//        }
//
//        System.out.println("\n----- TEST FIND BY SHOW -----");
//        if (showForTicket != null) {
//            for (Ticket t : ticketRepo.findByShow(showForTicket)) {
//                System.out.println(
//                        "ID: " + t.getId() +
//                                ", Buyer: " + t.getBuyerName() +
//                                ", Seats: " + t.getSeatCount() +
//                                ", SoldAt: " + t.getSoldAt() +
//                                ", ShowArtist: " + t.getShow().getArtistName()
//                );
//            }
//        }
//    }
//}