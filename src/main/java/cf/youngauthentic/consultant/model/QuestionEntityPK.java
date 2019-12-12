package cf.youngauthentic.consultant.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class QuestionEntityPK implements Serializable {
    private int departmentId;
    private int courseId;
    private int questionId;

    @Column(name = "department_id", nullable = false)
    @Id
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "course_id", nullable = false)
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Column(name = "question_id", nullable = false)
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public QuestionEntityPK(int departmentId, int courseId, int questionId) {
        this.departmentId = departmentId;
        this.courseId = courseId;
        this.questionId = questionId;
    }

    public QuestionEntityPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntityPK that = (QuestionEntityPK) o;
        return departmentId == that.departmentId &&
                courseId == that.courseId &&
                questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, courseId, questionId);
    }
}
