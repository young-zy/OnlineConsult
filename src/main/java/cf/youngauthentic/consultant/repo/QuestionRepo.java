package cf.youngauthentic.consultant.repo;


import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.question.QuestionEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepo extends CrudRepository<QuestionEntity, QuestionEntityPK> {

    List<QuestionEntity> findAllByDepartmentIdAndCourseId(int departmentId, int courseId, Pageable pageable);

    QuestionEntity findByDepartmentIdAndCourseIdAndQuestionId(int departmentId, int courseId, int questionId);
}
