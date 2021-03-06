package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.message.MessageEntity;
import cf.youngauthentic.consultant.model.message.SimpleMessage;
import cf.youngauthentic.consultant.repo.MessageRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {
    final
    MessageRepo messageRepo;

    final
    UserService userService;

    final
    LoginService loginService;

    public MessageService(MessageRepo messageRepo, UserService userService, LoginService loginService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
        this.loginService = loginService;
    }

    @Transactional
    public void addMessage(String title, int departmentId, int courseId, int questionId, int uid) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageTitle(title);
        messageEntity.setDepartmentId(departmentId);
        messageEntity.setCourseId(courseId);
        messageEntity.setQuestionId(questionId);
        messageEntity.setUid(uid);
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

    @Transactional
    public SimpleMessage getMessage(int mid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        SimpleMessage simpleMessage = messageRepo.findByMessageId(mid);
        if (simpleMessage.getUid() != uid) {
            throw new AuthException("权限不足");
        } else {
            messageRepo.acknowledge(simpleMessage.getMessageId());
            return simpleMessage;
        }

    }

}
