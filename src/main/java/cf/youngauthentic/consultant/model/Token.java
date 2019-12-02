package cf.youngauthentic.consultant.model;

import org.springframework.data.redis.core.RedisHash;


@RedisHash("Token")
public class Token {

    private String token;
    private int uid;
    private String authority;

    public Token(String token, int uid, String authority) {
        this.token = token;
        this.uid = uid;
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
