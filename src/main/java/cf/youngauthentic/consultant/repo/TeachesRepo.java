package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.TeachesEntity;
import cf.youngauthentic.consultant.model.TeachesEntityPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeachesRepo extends CrudRepository<TeachesEntity, TeachesEntityPK> {
    List<TeachesEntity> findAllByDepartmentIdAndCourseId(int departmentId, int courseId);

    List<TeachesEntity> findAllByDepartmentId(int departmentId);

}
