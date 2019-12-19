package cf.youngauthentic.consultant.model;

public class GetAuthResponseModel extends ResponseModel {
    public String auth;

    public GetAuthResponseModel(Boolean success, String reason, String auth) {
        super(success, reason);
        this.auth = auth;
    }
}
