package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.MessageEntity;
import cf.youngauthentic.consultant.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    public void addMessage() {
        MessageEntity messageEntity = new MessageEntity();

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
