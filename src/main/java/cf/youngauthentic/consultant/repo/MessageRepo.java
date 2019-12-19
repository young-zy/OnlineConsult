package cf.youngauthentic.consultant.repo;

import cf.youngauthentic.consultant.model.message.MessageEntity;
import cf.youngauthentic.consultant.model.message.SimpleMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<MessageEntity, Integer> {
    List<SimpleMessage> findAllByUid(int uid, Pageable pageable);

    SimpleMessage findByMessageId(int messageId);

    int countAllByUidAndIsAcknowledged(int uid, int isAcknowledged);

    int countAllByUid(int uid);

    @Modifying
    @Query("update MessageEntity m set m.isAcknowledged=1 where m.messageId=?1")
    void acknowledge(int mid);
}
