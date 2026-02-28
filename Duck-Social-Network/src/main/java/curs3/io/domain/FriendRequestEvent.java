package curs3.io.domain;

public class FriendRequestEvent {
    private final Long userIdAffected;

    public FriendRequestEvent(Long userIdAffected) {
        this.userIdAffected = userIdAffected;
    }

    public Long getUserIdAffected() {
        return userIdAffected;
    }
}
