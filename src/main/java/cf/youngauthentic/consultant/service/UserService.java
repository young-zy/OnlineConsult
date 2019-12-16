package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.RegisterRequestModel;
import cf.youngauthentic.consultant.model.Token;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserService {

    Gson gson = new Gson();

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public UserEntity getUser(int uid) {
        return userRepository.findByUid(uid);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
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

    public Boolean setPassword(int uid, String oldPass, String newPass, String tokenStr)
            throws AuthException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (tokenStr.equals("")) {
            throw new AuthException("token无效");
        }
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        UserEntity userEntity = getUser(uid);
        if (userEntity.getUid() != token.getUid()) {
            throw new AuthException("无修改权限");
        } else if (!PasswordHash.validatePassword(oldPass, userEntity.getHashedPassword())) {
            throw new AuthException("密码不正确");
        } else {
            userEntity.setHashedPassword(PasswordHash.createHash(newPass));
            saveUser(userEntity);
        }
        return true;
    }

    public Boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}
