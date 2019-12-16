package cf.youngauthentic.consultant.model.course;

import cf.youngauthentic.consultant.model.department.DepartmentWithoutCourse;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = CourseEntity.class)
public interface CourseWithoutTeachers {
    DepartmentWithoutCourse getDepartment();

    int getCourseId();

    String getCname();
}
