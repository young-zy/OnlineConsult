package cf.youngauthentic.consultant.model.question;

import cf.youngauthentic.consultant.model.course.CourseWithoutTeachers;
import cf.youngauthentic.consultant.model.user.SimpleUser;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(types = QuestionEntity.class)
public interface QuestionWithSimpleUser {
    CourseWithoutTeachers getCourse();

    int getQuestionId();

    String getQuestionTitle();

    Timestamp getCreateTime();

    SimpleUser getQuestioner();

    String getQuestionContent();

    String getAnswerContent();

    SimpleUser getAnswerer();
}
