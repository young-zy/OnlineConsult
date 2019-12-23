package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.service.Auth;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.LoginService;
import cf.youngauthentic.consultant.service.TeachesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    final
    TeachesService teachesService;

    final
    LoginService loginService;

    public TeacherController(TeachesService teachesService, LoginService loginService) {
        this.teachesService = teachesService;
        this.loginService = loginService;
    }

    @GetMapping(path = "/department/{did}/course/{cid}/teacher")
    public ResponseEntity<Object> getTeachers(@PathVariable int did,
                                              @PathVariable int cid,
                                              @RequestHeader(value = "token", defaultValue = "") String token) throws AuthException {
        if (loginService.hasAuth(token, Auth.STUDENT)) {
            return new ResponseEntity<>(teachesService.getTeachers(did, cid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseModel(false, "未登陆"), HttpStatus.OK);
        }
    }

    /**
     * 在教师列表中增加教师
     * 若包含已有的则忽略，不包含的不会删除
     *
     * @param did      部门id
     * @param cid      课程id
     * @param token    token值
     * @param teachers 教师uid列表
     * @return 403 权限不足
     * 400 操作出错
     * 202 操作成功
     */

    @PutMapping(path = "/department/{did}/course/{cid}/teacher")
    public ResponseEntity<Object> putTeacher(@PathVariable int did, @PathVariable int cid,
                                             @RequestHeader(value = "token", defaultValue = "") String token,
                                             @RequestBody List<Integer> teachers
    ) {
        try {
            loginService.hasAuth(token, Auth.ADMIN);
            teachesService.addTeachers(did, cid, teachers);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
