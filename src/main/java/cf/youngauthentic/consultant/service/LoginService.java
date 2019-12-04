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

    public Boolean hasToken(String tokenStr) {
        return !stringRedisTemplate.opsForValue().get(tokenStr).equals("");
    }

    public void logOut(String tokenStr) {
        stringRedisTemplate.delete(tokenStr);
    }

    public String getAuthority(String tokenStr) {
        Token token = gson.fromJson(stringRedisTemplate.opsForValue().get(tokenStr), Token.class);
        return token.getAuthority();
    }
}
