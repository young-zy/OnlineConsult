package cf.youngauthentic.consultant.model.department;

import cf.youngauthentic.consultant.model.course.CourseWithoutTeachers;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = DepartmentEntity.class)
public interface DepartmentWithCourse {
    int getDepartmentId();

    String getDepartmentName();

    List<CourseWithoutTeachers> getCourseEntities();
}
