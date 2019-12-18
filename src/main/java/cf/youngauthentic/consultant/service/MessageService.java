package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.message.MessageEntity;
import cf.youngauthentic.consultant.model.message.SimpleMessage;
import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Transactional
    public void addMessage(String title, QuestionEntity question, UserEntity userEntity) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageTitle(title);
        messageEntity.setQuestion(question);
        messageEntity.setUser(userEntity);
        messageRepo.save(messageEntity);
    }

    public List<SimpleMessage> getMessages(String token, int page) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        if (uid == -1) {
            return null;
        }
        return messageRepo.findAllByUid(uid, PageRequest.of(page, 10, Sort.by("isAcknowledged").ascending().and(Sort.by("messageId").descending())));
    }

    public int getUnreadMessageCount(String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        return messageRepo.countAllByUidAndIsAcknowledged(uid, 0);
    }

    public int getMessageCount(String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        return messageRepo.countAllByUid(uid);
    }

    public SimpleMessage getMessage(int mid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        SimpleMessage simpleMessage = messageRepo.findByMessageId(mid);
        if (simpleMessage.getUid() != uid) {
            throw new AuthException("权限不足");
        } else {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageId(mid);
            messageEntity.setDepartmentId(simpleMessage.getDepartmentId());
            messageEntity.setCourseId(simpleMessage.getCourseId());
            messageEntity.setQuestionId(simpleMessage.getQuestionId());
            messageEntity.setMessageTitle(simpleMessage.getMessageTitle());
            messageEntity.setIsAcknowledged(1);
            messageRepo.save(messageEntity);
            return simpleMessage;
        }

    }

}
