//package curs3.io.repository;
//
//import curs3.io.domain.*;
//import curs3.io.domain.validators.Validator;
//
//import java.io.*;
//import java.time.LocalDate;
//import java.util.*;
//
//public class FileRepository extends InMemoryRepository<Long, Utilizator> {
//
//    private final String fileName;
//
//    public FileRepository(Validator<Utilizator> validator, String fileName) {
//        super(validator);
//        this.fileName = fileName;
//        loadFromFile();
//    }
//
//    /**
//     * Citește toți utilizatorii din fișier și îi adaugă în memorie.
//     */
//    private void loadFromFile() {
//        File file = new File(fileName);
//        if (!file.exists()) return; // dacă nu există, nu facem nimic
//
//        Map<Long, List<Long>> pendingFriends = new HashMap<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                if (line.isBlank()) continue;
//                // format: TYPE;id;firstName;lastName;attr1;attr2;attr3;friends
//                String[] parts = line.split(";");
//                String type = parts[0];
//                Long id = Long.valueOf(parts[1]);
//                String username = parts[2];
//                String email = parts[3];
//                String pasword = parts[4];
//
//                Utilizator utilizator = null;
//
//                if (type.equals("P")) {
//                    // Persoana: P;id;first;last;jobTitle;birthDate;empathy;friends
//                    String firstName = parts[5];
//                    String lastName = parts[6];
//                    String jobTitle = parts[7];
//                    LocalDate birthDate = LocalDate.parse(parts[8]);
//                    Integer empathy = Integer.valueOf(parts[9]);
//                    //utilizator = new Persoana(id, username, email, pasword, firstName, lastName, jobTitle, birthDate, empathy);
//
//                    if (parts.length > 10 && !parts[10].isEmpty()) {
//                        List<Long> friendIds = Arrays.stream(parts[10].split(","))
//                                .map(Long::valueOf)
//                                .toList();
//                        pendingFriends.put(id, friendIds);
//                    }
//                } else if (type.equals("D")) {
//                    // Duck: D;id;first;last;speed;resistance;type;friends
//                    Double speed = Double.valueOf(parts[5]);
//                    Double resistance = Double.valueOf(parts[6]);
//                    String duckType = parts[7];
//                    if(duckType.equals("Flying"))
//                        utilizator = new FlyingDuck(id, username, email, pasword, speed, resistance, duckType);
//                    else if (duckType.equals("Swimming"))
//                        utilizator = new SwimmingDuck(id, username, email, pasword, speed, resistance, duckType);
//
//                    if (parts.length > 8 && !parts[8].isEmpty()) {
//                        List<Long> friendIds = Arrays.stream(parts[8].split(","))
//                                .map(Long::valueOf)
//                                .toList();
//                        pendingFriends.put(id, friendIds);
//                    }
//                } else {
//                    continue; // linie invalidă
//                }
//
//                super.save(utilizator);
//            }
//
//            // reconectăm prieteniile
//            for (var entry : pendingFriends.entrySet()) {
//                Utilizator u = super.findOne(entry.getKey());
//                for (Long friendId : entry.getValue()) {
//                    Utilizator f = super.findOne(friendId);
//                    if (u != null && f != null && !u.isFriend(f)) {
//                        u.addFriend(f);
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Scrie toți utilizatorii în fișier, păstrând și tipul lor.
//     */
//    private void saveToFile() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//
//            for (Utilizator u : super.findAll()) {
//                String friendsString = "";
//                if (u.getFriends() != null && !u.getFriends().isEmpty()) {
//                    friendsString = u.getFriends().stream()
//                            .map(fr -> fr.getId().toString())
//                            .reduce((a, b) -> a + "," + b)
//                            .orElse("");
//                }
//
//                if (u instanceof Persoana p) {
//                    writer.write("P;" + p.getId() + ";" + p.getUsername() + ";" + p.getEmail() + ";" + p.getPassword() + ";" +
//                            p.getLastName() + ";" + p.getFirstName() + ";" + p.getJobTitle() + ";" + p.getBirthDate() + ";" +
//                            p.getEmpathyLevel() + ";" + friendsString);
//                } else if (u instanceof Duck d) {
//                    writer.write("D;" + d.getId() + ";" + d.getUsername() + ";" + d.getEmail() + ";" + d.getPassword() + ";" +
//                            d.getSpeed() + ";" + d.getResistance() + ";" + d.getType() + ";" + friendsString);
//                } else {
//                    // fallback pentru tip necunoscut
//                    writer.write("U;" + u.getId() + ";" + u.getUsername() + ";" + u.getEmail() + ";" + u.getPassword() + ";" + friendsString);
//                }
//                writer.newLine();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Suprascriem metodele care modifică datele pentru a salva automat în fișier
//
//    @Override
//    public Utilizator save(Utilizator entity) {
//        Utilizator u = super.save(entity);
//        saveToFile();
//        return u;
//    }
//
//    @Override
//    public Utilizator delete(Long id) {
//        Utilizator u = super.delete(id);
//        saveToFile();
//        return u;
//    }
//
//    @Override
//    public Utilizator update(Utilizator entity) {
//        Utilizator u = super.update(entity);
//        saveToFile();
//        return u;
//    }
//
//    @Override
//    public void addFriendRepo(Utilizator u1, Utilizator u2) throws Exception {
//        super.addFriendRepo(u1, u2);
//        saveToFile();
//    }
//
//    @Override
//    public void removeFriendRepo(Utilizator u1, Utilizator u2) throws Exception {
//        super.removeFriendRepo(u1, u2);
//        saveToFile();
//    }
//
//    @Override
//    public void removeUserAndRelations(Utilizator u) {
//        super.removeUserAndRelations(u);
//        saveToFile();
//    }
//}
