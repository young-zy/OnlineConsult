package cf.youngauthentic.consultant.model.question;

import cf.youngauthentic.consultant.model.course.CourseEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "question", schema = "consult")
@IdClass(QuestionEntityPK.class)
public class QuestionEntity {
    private int departmentId;
    private int courseId;
    private int questionId;
    private String questionTitle;
    private String questionContent;
    private String answerContent;
    private Timestamp createTime;
    private CourseEntity course;
    private UserEntity questioner;
    private UserEntity answerer;

    @Id
    @Column(name = "department_id", nullable = false)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Id
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "question_id", nullable = false)
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "question_title", nullable = false, length = 45)
    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    @Basic
    @Column(name = "question_content", nullable = true, length = 45)
    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    @Basic
    @Column(name = "answer_content", nullable = true, length = 45)
    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return departmentId == that.departmentId &&
                courseId == that.courseId &&
                questionId == that.questionId &&
                Objects.equals(questionTitle, that.questionTitle) &&
                Objects.equals(questionContent, that.questionContent) &&
                Objects.equals(answerContent, that.answerContent) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, courseId, questionId, questionTitle, questionContent, answerContent, createTime);
    }

//    @ManyToOne
//    @JoinColumns({@JoinColumn(name = "course_id", referencedColumnName = "department_id", nullable = false), @JoinColumn(name = "department_id", referencedColumnName = "cid", nullable = false)})
//    public CourseEntity getCourse() {
//        return course;
//    }
//
//    public void setCourse(CourseEntity course) {
//        this.course = course;
//    }

    @ManyToOne
    @JoinColumn(name = "question_uid", referencedColumnName = "uid", nullable = false)
    public UserEntity getQuestioner() {
        return questioner;
    }

    public void setQuestioner(UserEntity userByQuestionUid) {
        this.questioner = userByQuestionUid;
    }

    @ManyToOne
    @JoinColumn(name = "answer_uid", referencedColumnName = "uid", nullable = false)
    public UserEntity getAnswerer() {
        return answerer;
    }

    public void setAnswerer(UserEntity userByAnswerUid) {
        this.answerer = userByAnswerUid;
    }
}
