package vttp.batch5.ssf.noticeboard.models;

public class Post {
    
    private String id;
    private long timestamp;
    private String message;
    private Boolean isSuccess = false;
    
    public Post(String message) {
        this.message = message;
    }

    public Post(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Post(String id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    };
    
}
