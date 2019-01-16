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

    public Integer getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reject reject = (Reject) o;

        if (id != reject.id) return false;
        if (requestId != reject.requestId) return false;
        return text.equals(reject.text);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + text.hashCode();
        result = 31 * result + requestId;
        return result;
    }
}
