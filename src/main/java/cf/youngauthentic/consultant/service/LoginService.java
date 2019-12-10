package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.LoginRequestModel;
import cf.youngauthentic.consultant.model.Token;
import cf.youngauthentic.consultant.model.UserEntity;
import cf.youngauthentic.consultant.repo.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    Gson gson = new Gson();
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String login(LoginRequestModel model) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserEntity user = userRepository.findByUsername(model.username);
        if (PasswordHash.validatePassword(model.password, user.getHashedPassword())) {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            String username = user.getUsername();
            long longToken = Math.abs(random.nextLong());
            String tokenStr = Long.toString(longToken, 16);
            Token token = new Token(username + ":" + tokenStr, user.getUid(), user.getAuthority());
            stringRedisTemplate.opsForValue().set(token.getToken(), gson.toJson(token), 1, TimeUnit.DAYS);
            return token.getToken();
        } else {
            return null;
        }
    }

    /**
     * @param tokenStr token字符串
     * @param level    设定的权限
     * @return 返回是否有权限
     */

    public Boolean hasAuth(String tokenStr, Enum<Auth> level) {
        if (tokenStr == null) {
            return false;
        }
        if (!isLogined(tokenStr)) {
            return false;
        }
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        if (level == Auth.ADMIN) {
            return token.getAuthority().equals("admin");
        } else if (level == Auth.TEACHER) {
            return !token.getAuthority().equals("student");
        } else {
            return true;
        }
    }

    private Boolean isLogined(String tokenStr) {
        if (tokenStr.equals("")) {
            return false;
        } else {
            if (hasToken(tokenStr)) {
                stringRedisTemplate.expire(tokenStr, 12, TimeUnit.HOURS);     //存在则更新token失效时间
                return true;
            } else {
                return false;
            }
        }
    }

    private Boolean hasToken(String tokenStr) {
        return stringRedisTemplate.hasKey(tokenStr);
    }

    public void logOut(String tokenStr) {
        stringRedisTemplate.delete(tokenStr);
    }

    public String getAuthority(String tokenStr) {
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        if (token == null) {
            return "";
        } else {
            return token.getAuthority();
        }
    }
}
