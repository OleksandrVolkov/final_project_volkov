package model.entity;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private int id;
    private String text;
    private String status;
    private List<Item> items;
    private Feedback feedback;
    private Reject reject;
    private Double price;
    private int userId;

    public Request(int id, String text, String status, List<Item> items, Feedback feedback, int userId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.items = items;
        this.feedback = feedback;
        this.userId = userId;
    }
    public Request(int id, String text, String status, Feedback feedback, int userId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.feedback = feedback;
        this.userId = userId;
        items = new ArrayList<>();
    }
    public Request(String text, String status, Feedback feedback, int userId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.feedback = feedback;
        this.userId = userId;
        items = new ArrayList<>();
    }

    public Request(int id, String text, String status, int userId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.userId = userId;
        items = new ArrayList<>();
    }

    public Request(String text, String status, int userId) {
        this.text = text;
        this.status = status;
        this.userId = userId;
        items = new ArrayList<>();
    }

    public Integer getId() {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Reject getReject() {
        return reject;
    }

    public void setReject(Reject reject) {
        this.reject = reject;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != request.id) return false;
        if (text != null ? !text.equals(request.text) : request.text != null) return false;
        if (!status.equals(request.status)) return false;
        if (!items.equals(request.items)) return false;
        return feedback != null ? feedback.equals(request.feedback) : request.feedback == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + items.hashCode();
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        return result;
    }
}
