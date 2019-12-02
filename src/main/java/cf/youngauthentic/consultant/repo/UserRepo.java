package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);
}
