package model.entity;

public class Feedback {
    private Integer id;
    private String text;
    private String date;



    public Feedback(int id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public Feedback(String text, String date) {
        this.text = text;
        this.date = date;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
