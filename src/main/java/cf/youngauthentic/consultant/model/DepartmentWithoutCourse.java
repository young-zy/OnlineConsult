package cf.youngauthentic.consultant.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = DepartmentEntity.class, name = "DepartmentWithoutCourse")
public interface DepartmentWithoutCourse {
    int getDepartmentId();

    String getDepartmentName();
}
