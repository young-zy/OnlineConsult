package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.question.QuestionForList;
import cf.youngauthentic.consultant.model.question.QuestionWithSimpleUser;
import cf.youngauthentic.consultant.model.teaches.TeachesEntity;
import cf.youngauthentic.consultant.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    MessageService messageService;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    CourseService courseService;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    public List<QuestionForList> getQuestions(int did, int cid, int page, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.findAllByDepartmentIdAndCourseId(did, cid, PageRequest.of(page, 10, Sort.by("createTime").descending()));
    }

    public QuestionWithSimpleUser getQuestion(int did, int cid, int qid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.findByDepartmentIdAndCourseIdAndQuestionId(did, cid, qid);
    }

    public int getQuestionsCount(int did, int cid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.countAllByDepartmentIdAndCourseId(did, cid);
    }

    /**
     * @param did     departmentId
     * @param cid     courseId
     * @param title   questionTitle
     * @param content questionContent
     * @return isSuccessful
     * @throws AuthException when has no auth
     */

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean saveQuestion(int did, int cid, String title, String content, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        QuestionEntity question = new QuestionEntity();
        question.setDepartmentId(did);
        question.setCourseId(cid);
        question.setQuestioner(userService.getUser(uid));
        question.setQuestionTitle(title);
        question.setQuestionContent(content);
        questionRepo.save(question);
        question = questionRepo.findFirstByDepartmentIdEqualsAndCourseIdEqualsOrderByQuestionIdDesc(did, cid);
        List<TeachesEntity> teachers = question.getCourse().getTeachers();
        QuestionEntity finalQuestion = question;
        teachers.forEach(it -> {
            messageService.addMessage("新问题：" + finalQuestion.getQuestionTitle() + " 等待回答", finalQuestion, it.getTeacher());
        });
        return true;
    }
}
