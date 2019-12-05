package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.DepartmentEntity;
import cf.youngauthentic.consultant.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(path = "/department")
    public List<DepartmentEntity> getDepartments() {
        return departmentService.getDepartments();
    }

}
