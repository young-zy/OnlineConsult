package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.MessageEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByUser(UserEntity user);

    int countAllByUidAndIsAcknowledged(int uid, int isAcknowledged);
}
