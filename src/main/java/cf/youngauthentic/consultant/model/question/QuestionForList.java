package cf.youngauthentic.consultant.model.question;

import cf.youngauthentic.consultant.model.user.SimpleUser;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(types = QuestionEntity.class)
public interface QuestionForList {
    int getDepartmentId();

    int getCourseId();

    int getQuestionId();

    String getQuestionTitle();

    Timestamp getCreateTime();

    SimpleUser getQuestioner();
}
