package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.user.SimpleUser;
import cf.youngauthentic.consultant.model.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    SimpleUser findByUid(int uid);


    @Modifying
    @Query("update UserEntity u set u.hashedPassword = ?2 where u.uid = ?1")
    void updateHashedPassword(int uid, String hashedPassword);

    @Query("select u.hashedPassword from UserEntity u where u.uid = ?1")
    String getHashedPassword(int uid);

    @Modifying
    @Query("update UserEntity u set u.username = ?2, u.authority = ?3 where u.uid = ?1")
    void updateUser(int uid, String username, String authority);
}
