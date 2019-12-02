package cf.youngauthentic.consultant.model;

public class RegisterResponseModel extends ResponseModel {
    public RegisterResponseModel(Boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
