package cf.youngauthentic.consultant.repo;


import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.question.QuestionEntityPK;
import cf.youngauthentic.consultant.model.question.QuestionForList;
import cf.youngauthentic.consultant.model.question.QuestionWithSimpleUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepo extends CrudRepository<QuestionEntity, QuestionEntityPK> {

    List<QuestionForList> findAllByDepartmentIdAndCourseId(int departmentId, int courseId, Pageable pageable);

    QuestionWithSimpleUser findByDepartmentIdAndCourseIdAndQuestionId(int departmentId, int courseId, int questionId);

    int countAllByDepartmentIdAndCourseId(int departmentId, int courseId);

    @Query("select max(question.questionId) from QuestionEntity question where question.departmentId=?1 and question.courseId=?2")
    int findQuestionId(int did, int cid);

    @Modifying
    @Query("update QuestionEntity q set q.answerContent = ?4 , q.answererUid=?5 where q.departmentId = ?1 and q.courseId=?2 and q.questionId=?3")
    void answer(int did, int cid, int qid, String content, int uid);

    @Query("select q.questionUid from QuestionEntity q where q.departmentId = ?1 and q.courseId = ?2 and q.questionId = ?3")
    int getQuestionerUid(int did, int cid, int qid);

}
