package cf.youngauthentic.consultant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "consult")
public class UserEntity {
    private int uid;
    private String username;
    private String hashedPassword;
    private String authority;
    private Integer msgNum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Basic
    @Column(name = "hashed_password", nullable = false, length = 150)
    public String getHashedPassword() {
        return null;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Basic
    @Column(name = "authority", nullable = false, length = 45)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Basic
    @Column(name = "msg_num")
    public Integer getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return uid == that.uid &&
                Objects.equals(username, that.username) &&
                Objects.equals(hashedPassword, that.hashedPassword) &&
                Objects.equals(authority, that.authority) &&
                Objects.equals(msgNum, that.msgNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, hashedPassword, authority, msgNum);
    }
}
