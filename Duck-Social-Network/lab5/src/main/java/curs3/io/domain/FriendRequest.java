package curs3.io.domain;

public class FriendRequest {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private FriendRequestStatus status;

    public FriendRequest(Long id, Long fromUserId, Long toUserId, FriendRequestStatus status) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFromUserId() { return fromUserId; }
    public void setFromUserId(Long fromUserId) { this.fromUserId = fromUserId; }

    public Long getToUserId() { return toUserId; }
    public void setToUserId(Long toUserId) { this.toUserId = toUserId; }

    public FriendRequestStatus getStatus() { return status; }
    public void setStatus(FriendRequestStatus status) { this.status = status; }
}
