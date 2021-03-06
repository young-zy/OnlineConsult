package cf.youngauthentic.consultant.model.course;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CourseEntityPK implements Serializable {
    private int departmentId;
    private int course_id;

    public CourseEntityPK(int departmentId, int course_id) {
        this.departmentId = departmentId;
        this.course_id = course_id;
    }

    public CourseEntityPK() {
    }

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
        return course_id;
    }

    public void setCourseId(int cid) {
        this.course_id = cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntityPK that = (CourseEntityPK) o;
        return departmentId == that.departmentId &&
                course_id == that.course_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, course_id);
    }
}
