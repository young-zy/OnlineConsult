package cf.youngauthentic.consultant.repo;


import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.question.QuestionEntityPK;
import cf.youngauthentic.consultant.model.question.QuestionForList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepo extends CrudRepository<QuestionEntity, QuestionEntityPK> {

    List<QuestionForList> findAllByDepartmentIdAndCourseId(int departmentId, int courseId, Pageable pageable);

    QuestionEntity findByDepartmentIdAndCourseIdAndQuestionId(int departmentId, int courseId, int questionId);
}
