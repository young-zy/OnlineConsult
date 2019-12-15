package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.department.DepartmentWithCourse;
import cf.youngauthentic.consultant.model.department.DepartmentWithoutCourse;
import cf.youngauthentic.consultant.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private LoginService loginService;

    public List<DepartmentWithoutCourse> getDepartments(String token) throws Exception {
        if (!loginService.hasAuth(token, Auth.STUDENT)) {
            throw new Exception("权限不足");
        }
        List<DepartmentWithoutCourse> res = new ArrayList<>();
//        departmentRepo.findAll().forEach(res::add);
//        return res;
        return departmentRepo.findBy();
    }

    public DepartmentWithCourse getDepartment(int id) {
        return departmentRepo.findByDepartmentId(id);
    }

}
