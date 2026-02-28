package curs3.io.repository;

import curs3.io.domain.Message;
import curs3.io.domain.Utilizator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRepository {

    private final Connection conn;

    public MessageRepository(Connection conn) {
        this.conn = conn;
    }

    private Utilizator loadUser(long id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE userid=?"
        );
        stmt.setLong(1, id);

        ResultSet r = stmt.executeQuery();
        if (!r.next()) return null;

        return new Utilizator(
                r.getLong("userid"),
                r.getString("username"),
                r.getString("email"),
                r.getString("password")
        );
    }

    private Message loadReply(Long replyId) throws SQLException {
        if (replyId == null) return null;

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM messages WHERE msg_id=?"
        );
        stmt.setLong(1, replyId);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) return null;

        Utilizator from = loadUser(rs.getLong("from_id"));
        Utilizator to = loadUser(rs.getLong("to_id"));

        if (from == null || to == null) return null;

        return new Message(
                rs.getLong("msg_id"),
                from,
                to,
                rs.getString("message"),
                rs.getTimestamp("data").toLocalDateTime(),
                null
        );
    }


    public void save(Message msg) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO messages(from_id, to_id, message, data, reply_id) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setLong(1, msg.getFrom().getId());
            stmt.setLong(2, msg.getTo().getId());
            stmt.setString(3, msg.getMessage());
            stmt.setTimestamp(4, Timestamp.valueOf(msg.getData()));

            if (msg.getReply() != null)
                stmt.setLong(5, msg.getReply().getId());
            else
                stmt.setNull(5, Types.NULL);

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                msg.setId(keys.getLong(1));

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save message: " + e.getMessage());
        }
    }

    public Message findById(Long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM messages WHERE msg_id=?"
            );
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;

            Utilizator from = loadUser(rs.getLong("from_id"));
            Utilizator to = loadUser(rs.getLong("to_id"));
            Message reply = loadReply(rs.getObject("reply_id", Long.class));

            return new Message(
                    rs.getLong("msg_id"),
                    from,
                    to,
                    rs.getString("message"),
                    rs.getTimestamp("data").toLocalDateTime(),
                    reply
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getChat(Long u1, Long u2) {
        List<Message> chat = new ArrayList<>();
        Map<Long, Utilizator> userCache = new HashMap<>();
        Map<Long, Message> replyCache = new HashMap<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM messages " +
                            "WHERE (from_id=? AND to_id=?) OR (from_id=? AND to_id=?) " +
                            "ORDER BY data"
            );
            stmt.setLong(1, u1);
            stmt.setLong(2, u2);
            stmt.setLong(3, u2);
            stmt.setLong(4, u1);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long fromId = rs.getLong("from_id");
                long toId = rs.getLong("to_id");
                Long replyId = rs.getObject("reply_id", Long.class);

                Utilizator from = userCache.computeIfAbsent(fromId, id -> {
                    try { return loadUser(id); } catch (SQLException e) { throw new RuntimeException(e); }
                });
                Utilizator to = userCache.computeIfAbsent(toId, id -> {
                    try { return loadUser(id); } catch (SQLException e) { throw new RuntimeException(e); }
                });

                Message reply = null;
                if (replyId != null) {
                    reply = replyCache.computeIfAbsent(replyId, id -> {
                        try {
                            // aici tot cu cache:
                            PreparedStatement s2 = conn.prepareStatement("SELECT * FROM messages WHERE msg_id=?");
                            s2.setLong(1, id);
                            ResultSet r2 = s2.executeQuery();
                            if (!r2.next()) return null;

                            long rf = r2.getLong("from_id");
                            long rt = r2.getLong("to_id");

                            Utilizator rFrom = userCache.computeIfAbsent(rf, uid -> {
                                try { return loadUser(uid); } catch (SQLException e) { throw new RuntimeException(e); }
                            });
                            Utilizator rTo = userCache.computeIfAbsent(rt, uid -> {
                                try { return loadUser(uid); } catch (SQLException e) { throw new RuntimeException(e); }
                            });

                            return new Message(
                                    r2.getLong("msg_id"),
                                    rFrom,
                                    rTo,
                                    r2.getString("message"),
                                    r2.getTimestamp("data").toLocalDateTime(),
                                    null
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                chat.add(new Message(
                        rs.getLong("msg_id"),
                        from, to,
                        rs.getString("message"),
                        rs.getTimestamp("data").toLocalDateTime(),
                        reply
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chat;
    }



//    public List<Message> getChat(Long u1, Long u2) {
//        List<Message> chat = new ArrayList<>();
//
//        try {
//            PreparedStatement stmt = conn.prepareStatement(
//                    "SELECT * FROM messages " +
//                            "WHERE (from_id=? AND to_id=?) OR (from_id=? AND to_id=?) " +
//                            "ORDER BY data"
//            );
//
//            stmt.setLong(1, u1);
//            stmt.setLong(2, u2);
//            stmt.setLong(3, u2);
//            stmt.setLong(4, u1);
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Utilizator from = loadUser(rs.getLong("from_id"));
//                Utilizator to = loadUser(rs.getLong("to_id"));
//                Message reply = loadReply(rs.getObject("reply_id", Long.class));
//
//                chat.add(
//                        new Message(
//                                rs.getLong("msg_id"),
//                                from,
//                                to,
//                                rs.getString("message"),
//                                rs.getTimestamp("data").toLocalDateTime(),
//                                reply
//                        )
//                );
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return chat;
//    }
}
