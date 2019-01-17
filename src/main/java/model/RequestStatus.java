package model;

public enum RequestStatus {
    IS_BEING_SEEN("is being seen"), REJECTED("rejected"), DONE("done"), NOT_SEEN("not seen");
    private String value;

    RequestStatus(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
