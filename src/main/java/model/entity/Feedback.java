package model.entity;

public class Feedback {
    private Integer id;
    private String text;
    private String date;
    private int requestId;

    public Feedback(int id, String text, String date, int requestId) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.requestId = requestId;
    }

    public Feedback(String text, String date, int requestId) {
        this.text = text;
        this.date = date;
        this.requestId = requestId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (!id.equals(feedback.id)) return false;
        if (!text.equals(feedback.text)) return false;
        return date.equals(feedback.date);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
