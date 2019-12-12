package cf.youngauthentic.consultant.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = CourseEntity.class)
public interface CourseWithoutTeachers {
    int getDepartmentId();

    int getCourseId();

    String getCname();
}
