package model.entity;

public class Reject {
    private int id;
    private String text;
    private int requestId;

    public Reject(int id, String text, int requestId){
        this.id = id;
        this.text = text;
        this.requestId = requestId;
    }
    public Reject(String text, int requestId){
        this.text = text;
        this.requestId = requestId;
    }
    public Reject(String text){
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Reject{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", requestId=" + requestId +
                '}';
    }
}
