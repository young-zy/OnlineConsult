package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(path = "/department")
    public ResponseEntity<Object> getDepartments(@RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(departmentService.getDepartments(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/department/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable int id, @RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(departmentService.getDepartment(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

}
