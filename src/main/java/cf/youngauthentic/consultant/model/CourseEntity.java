package cf.youngauthentic.consultant.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "consult")
@IdClass(CourseEntityPK.class)
public class CourseEntity {
    private int departmentId;
    private int course_id;
    private String cname;
    private DepartmentEntity departmentByDepartmentId;

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
    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int cid) {
        this.course_id = cid;
    }

    @Basic
    @Column(name = "cname", nullable = false, length = 45)
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return departmentId == that.departmentId &&
                course_id == that.course_id &&
                Objects.equals(cname, that.cname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, course_id, cname);
    }

//    @ManyToOne
//    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
//    public DepartmentEntity getDepartmentByDepartmentId() {
//        return departmentByDepartmentId;
//    }
//
//    public void setDepartmentByDepartmentId(DepartmentEntity departmentByDepartmentId) {
//        this.departmentByDepartmentId = departmentByDepartmentId;
//    }
}
