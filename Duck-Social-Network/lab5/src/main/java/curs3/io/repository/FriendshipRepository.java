package curs3.io.repository;

import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public class FriendshipRepository {
    private final Connection conn;

    public void addFriendship(Long id1, Long id2) {
        try{

            Long first = Math.min(id1, id2);
            Long second = Math.max(id1, id2);

            var stmt = conn.prepareStatement(
                    "INSERT INTO friendships(userID1, userID2) VALUES (?, ?)"
            );
            stmt.setLong(1, first);
            stmt.setLong(2, second);

            stmt.executeUpdate();

        } catch (SQLException e) {

            if (e.getMessage().contains("duplicate")) {
                throw new RuntimeException("Aceasta prietenie exista deja!");
            }

            throw new RuntimeException("Cannot add friendship: " + e.getMessage());
        }

    }

    public void removeFriendship(Long id1, Long id2) {
        try{

            Long first = Math.min(id1, id2);
            Long second = Math.max(id1, id2);

            var stmt = conn.prepareStatement(
                    "DELETE FROM friendships WHERE userID1 = ? AND userID2 = ?"
            );
            stmt.setLong(1, first);
            stmt.setLong(2, second);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot remove friendship: " + e.getMessage());
        }
    }

    public List<Long> findFriendsOf(Long userID) {
        try {
            var stmt = conn.prepareStatement(
                    "SELECT userID1, userID2 FROM friendships WHERE userID1 = ? OR userID2 = ?"
            );
            stmt.setLong(1, userID);
            stmt.setLong(2, userID);

            var rs = stmt.executeQuery();
            List<Long> friends = new ArrayList<>();

            while (rs.next()) {
                long a = rs.getLong("userID1");
                long b = rs.getLong("userID2");

                // dacă userID = userID1 ⇒ prietenul este userID2
                // dacă userID = userID2 ⇒ prietenul este userID1
                long friendID = (a == userID ? b : a);

                if (friendID != userID)
                    friends.add(friendID);
            }

            return friends;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load friendships: " + e.getMessage());
        }


    }

    public List<String> findAllFriendshipsRaw() {
        List<String> list = new ArrayList<>();

        try {
            var stmt = conn.prepareStatement(
                    "SELECT friendshipID, userID1, userID2 FROM friendships ORDER BY friendshipID"
            );

            var rs = stmt.executeQuery();

            while (rs.next()) {
                long fid = rs.getLong("friendshipID");
                long u1 = rs.getLong("userID1");
                long u2 = rs.getLong("userID2");

                list.add(fid + " | " + u1 + " | " + u2);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load friendships: " + e.getMessage());
        }

        return list;
    }




//    public List<Long> findFriendsOf(Long userID) {
//        try{
//
//            var stmt = conn.prepareStatement(
//                    "SELECT userID2 FROM friendships WHERE userID1 = ? OR userID2 = ?"
//            );
//            stmt.setLong(1, userID);
//            stmt.setLong(2, userID);
//
//            var result = stmt.executeQuery();
//            List<Long> friends = new ArrayList<>();
//
//            while (result.next()) {
//
//                long id1 = result.getLong("userID1");
//                long id2 = result.getLong("userID2");
//                friends.add(id1 == userID ? id2 : id1);
//            }
//
//            return friends;
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Cannot load friendships: " + e.getMessage());
//        }
//    }
}
