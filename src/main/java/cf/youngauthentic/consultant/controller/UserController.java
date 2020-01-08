package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.*;
import cf.youngauthentic.consultant.service.Auth;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.LoginService;
import cf.youngauthentic.consultant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    final
    UserService userService;

    final
    LoginService loginService;

    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

//    @GetMapping(path = "/user/{uid}")
//    public @ResponseBody
//    UserEntity get(@PathVariable int uid, @RequestHeader(value = "token", defaultValue = "") String token) {
//        return userService.getUser(uid);
//    }

    @GetMapping(path = "/user")
    public @ResponseBody
    ResponseEntity<Object> getOwn(@RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(userService.getUserByToken(token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/user/{uid}")
    public @ResponseBody
    ResponseEntity<Object> getUser(@PathVariable int uid, @RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(userService.getSimpleUserByUid(uid, token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/user/auth")
    public @ResponseBody
    ResponseEntity<Object> getAuth(@RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            return new ResponseEntity<>(new GetAuthResponseModel(true, "", userService.getAuth(token)), HttpStatus.ACCEPTED);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = "/user/password")
    public ResponseEntity<Object> setPassword(@RequestBody SetPasswordRequestModel req,
                                              @RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            userService.setPassword(req.getOldPassword(), req.getNewPassword(), token);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/user/{uid}")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserRequestModel user, @PathVariable int uid, @RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            userService.updateUser(uid, user.username, user.authority, token);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestModel login,
                                        @RequestHeader(value = "token", defaultValue = "") String token) throws Exception {
        LoginResponseModel loginResponseModel;
        if (loginService.isLogined(token)) {          //有token且token有效
            loginResponseModel = new LoginResponseModel(false, null, "您已经登陆");
            return new ResponseEntity<>(loginResponseModel, HttpStatus.FORBIDDEN);
        }
        token = loginService.login(login.username, login.password);
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
    public ResponseEntity<Object> register(@RequestBody RegisterRequestModel registerRequestModel, @RequestHeader(value = "token", defaultValue = "") String token) {
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