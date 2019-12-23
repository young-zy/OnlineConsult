package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.Token;
import cf.youngauthentic.consultant.model.user.UserEntity;
import com.google.gson.Gson;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    Gson gson = new Gson();
    private final UserService userService;

    private final StringRedisTemplate stringRedisTemplate;

    public LoginService(@Lazy UserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String login(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserEntity user = userService.getUser(username);
        if (PasswordHash.validatePassword(password, user.getHashedPassword())) {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
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
     * @return 有权限返回true
     * @throws AuthException 无权限抛出异常
     */

    public Boolean hasAuth(String tokenStr, Enum<Auth> level) throws AuthException {
        if (tokenStr == null || !isLogined(tokenStr)) {
            throw new AuthException("未登陆或登陆已过期！");
        }
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        if (level == Auth.ADMIN) {
            if (!token.getAuthority().equals("admin")) {
                throw new AuthException("权限不足！");
            }
        } else if (level == Auth.TEACHER) {
            if (token.getAuthority().equals("student")) {
                throw new AuthException("权限不足！");
            }
        }
        return true;
    }

    public Boolean isLogined(String tokenStr) {
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

    public int getUid(String tokenStr) {
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        if (token == null) {
            return -1;
        }
        return token.getUid();
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


