package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.department.DepartmentEntity;
import cf.youngauthentic.consultant.model.department.DepartmentWithCourse;
import cf.youngauthentic.consultant.model.department.DepartmentWithoutCourse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends CrudRepository<DepartmentEntity, Integer> {
    List<DepartmentWithoutCourse> findBy();

    DepartmentWithCourse findByDepartmentId(int departmentId);
}
