package curs3.io.repository;

import curs3.io.domain.FriendRequest;
import curs3.io.domain.FriendRequestStatus;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FriendRequestRepositoryDB implements FriendRequestRepository {

    private final Connection conn;

    @Override
    public void save(FriendRequest fr) {
        try {
            var stmt = conn.prepareStatement(
                    "INSERT INTO friend_requests(from_user, to_user, status) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setLong(1, fr.getFromUserId());
            stmt.setLong(2, fr.getToUserId());
            stmt.setString(3, fr.getStatus().name());

            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                fr.setId(rs.getLong(1));
            }

        } catch (SQLException e) {

            if (e.getMessage().toLowerCase().contains("duplicate")) {
                throw new RuntimeException("Friend request already exists!");
            }

            throw new RuntimeException("Cannot save friend request: " + e.getMessage());
        }
    }

    @Override
    public List<FriendRequest> findPendingForUser(Long userId) {
        List<FriendRequest> list = new ArrayList<>();

        try {
            var stmt = conn.prepareStatement(
                    "SELECT id, from_user, to_user, status " +
                            "FROM friend_requests " +
                            "WHERE to_user = ? AND status = 'PENDING' " +
                            "ORDER BY id DESC"
            );

            stmt.setLong(1, userId);

            var rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new FriendRequest(
                        rs.getLong("id"),
                        rs.getLong("from_user"),
                        rs.getLong("to_user"),
                        FriendRequestStatus.valueOf(rs.getString("status"))
                ));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load pending friend requests: " + e.getMessage());
        }
    }

    @Override
    public void updateStatus(Long requestId, FriendRequestStatus status) {
        try {
            var stmt = conn.prepareStatement(
                    "UPDATE friend_requests SET status = ? WHERE id = ?"
            );

            stmt.setString(1, status.name());
            stmt.setLong(2, requestId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot update request status: " + e.getMessage());
        }
    }

    @Override
    public Optional<FriendRequest> findByUsers(Long from, Long to) {
        try {
            var stmt = conn.prepareStatement(
                    "SELECT id, from_user, to_user, status " +
                            "FROM friend_requests " +
                            "WHERE from_user = ? AND to_user = ? " +
                            "ORDER BY id DESC LIMIT 1"
            );

            stmt.setLong(1, from);
            stmt.setLong(2, to);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new FriendRequest(
                        rs.getLong("id"),
                        rs.getLong("from_user"),
                        rs.getLong("to_user"),
                        FriendRequestStatus.valueOf(rs.getString("status"))
                ));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find friend request: " + e.getMessage());
        }
    }
}
