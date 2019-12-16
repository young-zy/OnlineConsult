package cf.youngauthentic.consultant.model.teaches;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TeachesEntityPK implements Serializable {
    private int departmentId;
    private int courseId;
    private int uid;

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

    @Column(name = "uid", nullable = false)
    @Id
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachesEntityPK that = (TeachesEntityPK) o;
        return departmentId == that.departmentId &&
                courseId == that.courseId &&
                uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, courseId, uid);
    }
}
