package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.service.Auth;
import cf.youngauthentic.consultant.service.LoginService;
import cf.youngauthentic.consultant.service.TeachesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    @Autowired
    TeachesService teachesService;

    @Autowired
    LoginService loginService;

    @GetMapping(path = "/department/{did}/course/{cid}/teacher")
    public ResponseEntity<Object> getTeachers(@PathVariable int did, @PathVariable int cid, @RequestHeader(value = "token", defaultValue = "") String token) {
        if (loginService.hasAuth(token, Auth.STUDENT)) {
            return new ResponseEntity<>(teachesService.getTeachers(did, cid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseModel(false, "未登陆"), HttpStatus.OK);
        }

    }
}
