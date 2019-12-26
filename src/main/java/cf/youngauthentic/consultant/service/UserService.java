package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.RegisterRequestModel;
import cf.youngauthentic.consultant.model.user.SimpleUser;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.UserRepo;
import com.google.gson.Gson;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserService {

    Gson gson = new Gson();

    private final UserRepo userRepository;

    private final LoginService loginService;

    private final StringRedisTemplate stringRedisTemplate;

    public UserService(UserRepo userRepository, LoginService loginService, StringRedisTemplate stringRedisTemplate) {
        this.userRepository = userRepository;
        this.loginService = loginService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private SimpleUser getUser(int uid) {
        return userRepository.findByUid(uid);
    }

    public SimpleUser getSimpleUserByUid(int uid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return getUser(uid);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public SimpleUser getUserByToken(String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return getUser(loginService.getUid(token));
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public void register(RegisterRequestModel registerRequestModel) throws Exception {
        if (checkUsername(registerRequestModel.username)) {
            throw new Exception("用户名已被占用");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequestModel.username);
        userEntity.setHashedPassword(PasswordHash.createHash(registerRequestModel.password));
        userEntity.setAuthority("student");
        userEntity.setMsgNum(0);
        saveUser(userEntity);
    }

    public Boolean setPassword(String oldPass, String newPass, String tokenStr)
            throws AuthException, InvalidKeySpecException, NoSuchAlgorithmException {
        loginService.hasAuth(tokenStr, Auth.STUDENT);
        int uid = loginService.getUid(tokenStr);
        String hashedPassword = userRepository.getHashedPassword(uid);
        if (!PasswordHash.validatePassword(oldPass, hashedPassword)) {
            throw new AuthException("密码不正确");
        } else {
            userRepository.updateHashedPassword(uid, PasswordHash.createHash(newPass));
        }
        return true;
    }

    public Boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public String getAuth(String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return loginService.getAuthority(token);
    }

    @Transactional
    public Boolean updateUser(int uid, String username, String authority, String token) throws AuthException {
        loginService.hasAuth(token, Auth.ADMIN);
        if (!authority.equals("admin") && !authority.equals("teacher") && !authority.equals("student")) {
            throw new IllegalArgumentException("权限设置错误");
        }
        userRepository.updateUser(uid, username, authority);
        return true;
    }
}
