package cf.youngauthentic.consultant.model;

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
    private UserEntity userByUid;

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

//    @ManyToOne
//    @JoinColumns({@JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false), @JoinColumn(name = "course_id", referencedColumnName = "cid", nullable = false)})
//    public CourseEntity getCourse() {
//        return course;
//    }
//
//    public void setCourse(CourseEntity course) {
//        this.course = course;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
//    public UserEntity getUserByUid() {
//        return userByUid;
//    }
//
//    public void setUserByUid(UserEntity userByUid) {
//        this.userByUid = userByUid;
//    }
}
