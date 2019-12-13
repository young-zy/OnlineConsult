package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.CourseEntity;
import cf.youngauthentic.consultant.model.CourseEntityPK;
import cf.youngauthentic.consultant.model.CourseWithoutTeachers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepo extends CrudRepository<CourseEntity, CourseEntityPK> {
    List<CourseWithoutTeachers> findAllByDepartmentId(int departmentId);

    CourseEntity findByDepartmentIdAndCourseId(int departmentId, int courseId);
}
