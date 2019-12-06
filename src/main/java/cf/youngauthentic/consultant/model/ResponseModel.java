package cf.youngauthentic.consultant.model;

public class ResponseModel {
    public Boolean success;
    public String reason;

    public ResponseModel(Boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
