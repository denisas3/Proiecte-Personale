package curs3.io.repository;

import curs3.io.domain.FriendRequest;
import curs3.io.domain.FriendRequestStatus;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository {
    void save(FriendRequest fr);
    List<FriendRequest> findPendingForUser(Long userId);
    void updateStatus(Long requestId, FriendRequestStatus status);
    Optional<FriendRequest> findByUsers(Long from, Long to);
}
