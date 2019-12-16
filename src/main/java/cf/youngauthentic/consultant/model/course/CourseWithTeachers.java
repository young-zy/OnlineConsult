package cf.youngauthentic.consultant.model.course;

import cf.youngauthentic.consultant.model.teaches.Teacher;

import java.util.List;

public interface CourseWithTeachers {
    int getDepartmentId();

    int getCourseId();

    String getCname();

    List<Teacher> getTeachers();
}
