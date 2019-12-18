package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.message.MessageEntity;
import cf.youngauthentic.consultant.model.message.SimpleMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByUid(int uid, Pageable pageable);

    SimpleMessage findByMessageId(int messageId);

    int countAllByUidAndIsAcknowledged(int uid, int isAcknowledged);

    int countAllByUid(int uid);
}
