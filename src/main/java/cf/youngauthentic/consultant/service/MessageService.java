package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.MessageEntity;
import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<MessageEntity> getMessages(String token) {
        int uid = loginService.getUid(token);
        List<MessageEntity> result;
        if (uid == -1) {
            return null;
        }
        return messageRepo.findAllByUser(userService.getUser(uid));
    }

}
