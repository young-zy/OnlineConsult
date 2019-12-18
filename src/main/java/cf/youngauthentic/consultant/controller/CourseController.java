package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.model.course.CourseWithTeachers;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/department/{did}/course")
    public ResponseEntity<Object> getCourses(@PathVariable int did,
                                             @RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(courseService.getCourses(did, token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/department/{did}/course/{cid}")
    public ResponseEntity<Object> getCourse(@PathVariable int did,
                                            @PathVariable int cid,
                                            @RequestHeader(defaultValue = "") String token) {
        try {
            CourseWithTeachers courseWithTeachers = courseService.getCourseWithTeachers(did, cid, token);
            if (courseWithTeachers == null) {
                return new ResponseEntity<>(new ResponseModel(false, "未找到"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(courseWithTeachers, HttpStatus.OK);
            }
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/department/{did}/course/count")
    public ResponseEntity<Object> getCourseCount(@PathVariable int did,
                                                 @RequestHeader(defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(courseService.countCourses(did, token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
