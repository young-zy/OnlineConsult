package cf.youngauthentic.consultant.model.course;

import cf.youngauthentic.consultant.model.TeachesEntity;
import cf.youngauthentic.consultant.model.department.DepartmentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "consult")
@IdClass(CourseEntityPK.class)
public class CourseEntity {
    private int departmentId;
    private int courseId;
    private String cname;
    private DepartmentEntity department;
    private List<TeachesEntity> teachers;

    @JsonIgnore
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

    public void setCourseId(int cid) {
        this.courseId = cid;
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
                courseId == that.courseId &&
                Objects.equals(cname, that.cname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, courseId, cname);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false, insertable = false, updatable = false)
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity departmentByDepartmentId) {
        this.department = departmentByDepartmentId;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false, insertable = false, updatable = false)
    })
    public List<TeachesEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeachesEntity> teachers) {
        this.teachers = teachers;
    }


}
