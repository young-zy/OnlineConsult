package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.AddCourseRequestModel;
import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.model.course.CourseWithTeachers;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/department/{did}/course")
    public ResponseEntity<Object> getCourses(@PathVariable int did,
                                             @RequestHeader(defaultValue = "") String token,
                                             @RequestParam(value = "page", defaultValue = "1") String pageStr
    ) {
        try {
            int page = Integer.parseInt(pageStr);
            return new ResponseEntity<>(courseService.getCourses(did, token, --page), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/department/{did}/course")
    public ResponseEntity<Object> addCourse(@PathVariable int did,
                                            @RequestBody AddCourseRequestModel model,
                                            @RequestHeader(defaultValue = "") String token
    ) {
        try {
            courseService.addCourse(did, model.cname, token);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.CREATED);
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

    @DeleteMapping(path = "/department/{did}/course/{cid}")
    public ResponseEntity<Object> deleteCourse(@PathVariable int did,
                                               @PathVariable int cid,
                                               @RequestHeader(defaultValue = "") String token) {
        try {
            courseService.deleteCourse(did, cid, token);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.OK);
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
