package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(path = "/department")
    public ResponseEntity<Object> getDepartments(@RequestHeader(defaultValue = "") String token,
                                                 @RequestParam(value = "page", defaultValue = "1") String pageStr
    ) {
        try {
            int page = Integer.parseInt(pageStr);
            return new ResponseEntity<>(departmentService.getDepartments(token, --page), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/department/count")
    public ResponseEntity<Object> getDepartmentCount(@RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(departmentService.getDepartmentCount(token), HttpStatus.OK);
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
