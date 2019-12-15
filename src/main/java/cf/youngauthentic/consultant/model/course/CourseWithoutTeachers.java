package cf.youngauthentic.consultant.model.course;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = CourseEntity.class)
public interface CourseWithoutTeachers {
    int getDepartmentId();

    int getCourseId();

    String getCname();
}
