package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.RegisterRequestModel;
import cf.youngauthentic.consultant.model.UserEntity;
import cf.youngauthentic.consultant.repo.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    Gson gson = new Gson();

    @Autowired
    private UserRepo userRepository;

    public Optional<UserEntity> getUser(int uid) {
        return userRepository.findById(uid);
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

    public Boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}
