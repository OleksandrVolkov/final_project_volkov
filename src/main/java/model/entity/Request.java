package model.entity;

import java.util.List;

public class Request {
    private int id;
//    private User user;
    private String text;
    private String status;
    private List<Item> items;
    private Feedback feedback;

    public Request(int id, String text, String status, List<Item> items, Feedback feedback) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.items = items;
        this.feedback = feedback;
    }
    public Request(int id, String text, String status, Feedback feedback) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.feedback = feedback;
    }
    public Request(String text, String status, Feedback feedback) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.feedback = feedback;
    }

    public Request(int id, String text, String status) {
        this.id = id;
//        this.user = user;
        this.text = text;
        this.status = status;
    }

    public Request(String text, String status) {
//        this.user = user;
        this.text = text;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status='" + status + '\'' +
                ", items=" + items +
                ", feedback=" + feedback +
                '}';
    }
}
