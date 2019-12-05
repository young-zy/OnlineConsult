package cf.youngauthentic.consultant.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "department", schema = "consult")
public class DepartmentEntity {
    private int departmentId;
    private String departmentName;

    @Id
    @Column(name = "department_id", nullable = false)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_name", nullable = false, length = 45)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = CourseEntity.class,
            mappedBy = "department_id"
    )
    private List<CourseEntity> courseEntities;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return departmentId == that.departmentId &&
                Objects.equals(departmentName, that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName);
    }
}
