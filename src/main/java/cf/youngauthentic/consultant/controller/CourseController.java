package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.CourseEntity;
import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/department/{id}/course")
    public ResponseEntity<Object> getDepartments(@PathVariable int id, @RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(courseService.getCourses(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/department/{did}/course/{cid}")
    public ResponseEntity<Object> getDepartment(@PathVariable int did, @PathVariable int cid, @RequestHeader(defaultValue = "") String token) {
        try {
            Optional<CourseEntity> res = courseService.getCourse(did, cid);
            if (res.isEmpty()) {
                return new ResponseEntity<>(new ResponseModel(false, "未找到"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(courseService.getCourse(did, cid), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

}