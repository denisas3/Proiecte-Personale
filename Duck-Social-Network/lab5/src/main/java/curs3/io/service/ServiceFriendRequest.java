package curs3.io.service;

import curs3.io.domain.FriendRequest;
import curs3.io.domain.FriendRequestEvent;
import curs3.io.domain.FriendRequestStatus;
import curs3.io.repository.FriendRequestRepository;
import java.util.Observable;


import java.util.List;
import java.util.Optional;

public class ServiceFriendRequest extends Observable    {

    private final FriendRequestRepository repo;
    private final ServiceFriendship friendshipService;

    public ServiceFriendRequest(FriendRequestRepository repo, ServiceFriendship friendshipService) {
        this.repo = repo;
        this.friendshipService = friendshipService;
    }

    public void sendRequest(Long from, Long to) {
        if (from == null || to == null)
            throw new RuntimeException("Invalid user ids!");
        if (from.equals(to))
            throw new RuntimeException("You cannot send request to yourself!");

        // evităm duplicate: dacă există deja pending/accepted/rejected pentru aceeași pereche, blocăm
        Optional<FriendRequest> existing = repo.findByUsers(from, to);
        if (existing.isPresent() && existing.get().getStatus() == FriendRequestStatus.PENDING)
            throw new RuntimeException("Request already pending!");

        repo.save(new FriendRequest(null, from, to, FriendRequestStatus.PENDING));
        setChanged();
        notifyObservers(new FriendRequestEvent(to));
    }

    public List<FriendRequest> getPendingFor(Long userId) {
        return repo.findPendingForUser(userId);
    }

    public void accept(FriendRequest req) {
        repo.updateStatus(req.getId(), FriendRequestStatus.ACCEPTED);
        friendshipService.addFriend(req.getFromUserId(), req.getToUserId());

        setChanged();
        notifyObservers(new FriendRequestEvent(req.getToUserId()));
    }

    public void reject(FriendRequest req) {
        repo.updateStatus(req.getId(), FriendRequestStatus.REJECTED);

        setChanged();
        notifyObservers(new FriendRequestEvent(req.getToUserId()));
    }

}
