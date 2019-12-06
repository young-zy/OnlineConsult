package cf.youngauthentic.consultant.model;

public class RegisterResponseModel extends ResponseModel {
    public RegisterResponseModel(Boolean success, String reason) {
        super(success, reason);
        this.success = success;
        this.reason = reason;
    }
}
