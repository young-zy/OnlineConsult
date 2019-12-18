package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.department.DepartmentWithCourse;
import cf.youngauthentic.consultant.model.department.DepartmentWithoutCourse;
import cf.youngauthentic.consultant.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private LoginService loginService;

    public List<DepartmentWithoutCourse> getDepartments(String token, int page) throws Exception {
        loginService.hasAuth(token, Auth.STUDENT);
        List<DepartmentWithoutCourse> res = new ArrayList<>();
//        departmentRepo.findAll().forEach(res::add);
//        return res;
        return departmentRepo.findBy(PageRequest.of(page, 10, Sort.by("departmentName").ascending()));
    }

    public DepartmentWithCourse getDepartment(int id) {
        return departmentRepo.findByDepartmentId(id);
    }

    public int getDepartmentCount(String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return departmentRepo.countAllBy();
    }

}
