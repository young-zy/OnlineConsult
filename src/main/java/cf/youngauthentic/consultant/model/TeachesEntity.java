package cf.youngauthentic.consultant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teaches", schema = "consult")
@IdClass(TeachesEntityPK.class)
public class TeachesEntity {
    private int departmentId;
    private int courseId;
    private int uid;
    private CourseEntity course;
    private UserEntity teacher;

    @JsonIgnore
    @Id
    @Column(name = "department_id", nullable = false)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @JsonIgnore
    @Id
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @JsonIgnore
    @Id
    @Column(name = "uid", nullable = false)
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
        TeachesEntity that = (TeachesEntity) o;
        return departmentId == that.departmentId &&
                courseId == that.courseId &&
                uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, courseId, uid);
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false, insertable = false, updatable = false)
    })
    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false, insertable = false, updatable = false)
    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }
}
