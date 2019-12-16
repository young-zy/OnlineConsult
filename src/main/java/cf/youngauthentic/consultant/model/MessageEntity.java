package cf.youngauthentic.consultant.model;

import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "consult")
public class MessageEntity {
    private int messageId;
    private String messageTitle;
    private QuestionEntity question;
    private UserEntity user;
    private int isAcknowledged;

    @Id
    @Column(name = "message_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "message_title", nullable = false, length = 45)
    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    @Basic
    @Column(name = "is_acknowledged")
    public int getIsAcknowledged() {
        return isAcknowledged;
    }

    public void setIsAcknowledged(int isAcknowledged) {
        this.isAcknowledged = isAcknowledged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return messageId == that.messageId &&
                Objects.equals(messageTitle, that.messageTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageTitle);
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false), @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false), @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)})
    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "user_uid", referencedColumnName = "uid", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userByUserUid) {
        this.user = userByUserUid;
    }
}
