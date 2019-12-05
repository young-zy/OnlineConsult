package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.DepartmentEntity;
import cf.youngauthentic.consultant.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<DepartmentEntity> getDepartments() {
        List<DepartmentEntity> res = new ArrayList<>();
        departmentRepo.findAll().forEach(res::add);
        return res;
    }

    public Optional<DepartmentEntity> getDepartment(int id) {
        return departmentRepo.findById(id);
    }

}
