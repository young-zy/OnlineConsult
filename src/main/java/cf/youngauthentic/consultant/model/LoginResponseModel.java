package cf.youngauthentic.consultant.model;

public class LoginResponseModel extends ResponseModel {
    public String token;

    public LoginResponseModel(Boolean success, String token, String reason) {
        super(success, reason);
        this.success = success;
        this.token = token;
        this.reason = reason;
    }
}
