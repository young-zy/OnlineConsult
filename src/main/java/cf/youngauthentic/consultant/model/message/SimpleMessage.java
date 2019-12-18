package cf.youngauthentic.consultant.model.message;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = MessageEntity.class)
public interface SimpleMessage {
    int getUid();

    int getDepartmentId();

    int getCourseId();

    int getQuestionId();

    String getMessageTitle();
}
