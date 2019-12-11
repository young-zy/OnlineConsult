package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.*;
import cf.youngauthentic.consultant.service.Auth;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.LoginService;
import cf.youngauthentic.consultant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @GetMapping(path = "/user/{uid}")
    public @ResponseBody
    Optional<UserEntity> get(@PathVariable int uid, @RequestHeader(value = "token", defaultValue = "") String token) {
        return userService.getUser(uid);
    }

    @PostMapping(path = "/user/{uid}/password")
    public ResponseEntity<Object> setPassword(@PathVariable int uid, @RequestHeader(value = "token", defaultValue = "") String token) {

        return new ResponseEntity<>(new ResponseModel(false, ""), HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/user/{uid}")
    public ResponseEntity<Object> put(@RequestBody UserEntity user, @PathVariable int uid) {
        if (user.getUid() == uid) {
            try {
                userService.saveUser(user);
                return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseModel(true, e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
        }

    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestModel login,
                                        @RequestHeader(value = "token", defaultValue = "") String token) throws InvalidKeySpecException, NoSuchAlgorithmException {
        LoginResponseModel loginResponseModel;
        try {
            if (loginService.hasAuth(token, Auth.STUDENT)) {          //有token且token有效
                loginResponseModel = new LoginResponseModel(false, null, "您已经登陆");
                return new ResponseEntity<>(loginResponseModel, HttpStatus.FORBIDDEN);
            }
        } catch (AuthException e) {
            token = loginService.login(login);
        }
        if (token != null) {
            loginResponseModel = new LoginResponseModel(true, token, "");
            return new ResponseEntity<>(loginResponseModel, HttpStatus.ACCEPTED);
        } else {
            loginResponseModel = new LoginResponseModel(false, null, "用户名或密码不正确");
            return new ResponseEntity<>(loginResponseModel, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/user/logout")
    public ResponseEntity<Object> logout(@RequestHeader(value = "token", defaultValue = "") String token) {
        if (token.equals("")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            loginService.logOut(token);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<Object> post(@RequestBody RegisterRequestModel registerRequestModel, @RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            if (loginService.hasAuth(token, Auth.STUDENT)) {          //有token且token有效
                return new ResponseEntity<>(new RegisterResponseModel(false, "您已经登陆"), HttpStatus.FORBIDDEN);
            }
        } catch (AuthException e) {
            try {
                userService.register(registerRequestModel);
            } catch (Exception ex) {
                return new ResponseEntity<>(new RegisterResponseModel(false, ex.getMessage()), HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(new RegisterResponseModel(true, ""), HttpStatus.CREATED);
    }
}
