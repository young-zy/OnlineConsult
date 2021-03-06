package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.course.CourseEntity;
import cf.youngauthentic.consultant.model.course.CourseEntityPK;
import cf.youngauthentic.consultant.model.course.CourseWithTeachers;
import cf.youngauthentic.consultant.model.course.CourseWithoutTeachers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepo extends CrudRepository<CourseEntity, CourseEntityPK> {
    List<CourseWithoutTeachers> findAllByDepartmentId(int departmentId, Pageable pageable);

    CourseWithTeachers findByDepartmentIdAndCourseId(int departmentId, int courseId);

    int countAllByDepartmentId(int departmentId);

    void deleteByDepartmentIdAndCourseId(int departmentId, int courseId);
}
