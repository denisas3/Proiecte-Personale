package curs3.io.service;

import curs3.io.domain.Utilizator;
import curs3.io.repository.FriendshipRepository;
import curs3.io.repository.RepositoryBD;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceFriendship {
    private final FriendshipRepository friendRepo;
    private final RepositoryBD<Long, Utilizator> userRepo;

    public ServiceFriendship(FriendshipRepository friendRepo, RepositoryBD<Long, Utilizator> userRepo) {
        this.friendRepo = friendRepo;
        this.userRepo = userRepo;
    }

    public void addFriend(Long id1, Long id2) {
        if (id1.equals(id2))
            throw new RuntimeException("A user cannot friend himself!");

        friendRepo.addFriendship(id1, id2);
    }

    public void removeFriend(Long id1, Long id2) {
        friendRepo.removeFriendship(id1, id2);
        friendRepo.removeFriendship(id2, id1);
    }

    public List<Utilizator> getFriendsOf(Long userID) {
        List<Long> ids = friendRepo.findFriendsOf(userID);
        List<Utilizator> users = new ArrayList<>();

        for (Long id : ids) {
            userRepo.findById(id).ifPresent(users::add);
        }

        return users;
    }

    public int getNumarComunitati() {

        List<Utilizator> all = new ArrayList<>();
        userRepo.getAll().forEach(all::add);

        if (all.isEmpty()) return 0;

        return gasesteComunitati(all).size();
    }

    public List<List<Utilizator>> gasesteComunitati(List<Utilizator> toti) {

        for (Utilizator u : toti) {
            u.getFriends().clear();
            u.getFriends().addAll(getFriendsOf(u.getId()));
        }

        Set<Long> vizitati = new HashSet<>();
        Map<Long, Utilizator> map = toti.stream()
                .collect(Collectors.toMap(Utilizator::getId, u -> u));

        List<List<Utilizator>> comunitati = new ArrayList<>();

        for (Utilizator u : toti) {
            if (!vizitati.contains(u.getId())) {
                List<Utilizator> comunitate = new ArrayList<>();
                dfs(u, vizitati, comunitate, map);
                comunitati.add(comunitate);
            }
        }

        return comunitati;
    }

    private void dfs(Utilizator u, Set<Long> vizitati,
                     List<Utilizator> comunitate,
                     Map<Long, Utilizator> map) {

        vizitati.add(u.getId());
        comunitate.add(u);

        if (u.getFriends() != null) {
            for (Utilizator f : u.getFriends()) {
                Utilizator full = map.get(f.getId());
                if (full != null && !vizitati.contains(full.getId())) {
                    dfs(full, vizitati, comunitate, map);
                }
            }
        }
    }

    public List<String> getAllFriendshipsRaw() {
        return friendRepo.findAllFriendshipsRaw();
    }

}
