package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.model.question.QuestionForList;
import cf.youngauthentic.consultant.model.question.QuestionWithSimpleUser;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.QuestionRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    final
    MessageService messageService;

    final
    QuestionRepo questionRepo;

    //    @Autowired
//    CourseService courseService;
    final
    TeachesService teachesService;

    final
    LoginService loginService;

    final
    UserService userService;

    public QuestionService(MessageService messageService, QuestionRepo questionRepo, TeachesService teachesService, LoginService loginService, UserService userService) {
        this.messageService = messageService;
        this.questionRepo = questionRepo;
        this.teachesService = teachesService;
        this.loginService = loginService;
        this.userService = userService;
    }

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

//    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean saveQuestion(int did, int cid, String title, String content, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        int uid = loginService.getUid(token);
        QuestionEntity question = new QuestionEntity();
        question.setDepartmentId(did);
        question.setAnswererUid(null);
        question.setCourseId(cid);
        question.setQuestionUid(uid);
        question.setQuestionTitle(title);
        question.setQuestionContent(content);
        questionRepo.save(question);
        int qid = questionRepo.findQuestionId(did, cid);
//        question = questionRepo.findFirstByDepartmentIdEqualsAndCourseIdEqualsOrderByQuestionIdDesc(did, cid);
        question.setQuestionId(qid);
        List<UserEntity> teachers = teachesService.getTeachers(did, cid);
        teachers.forEach(it -> {
            messageService.addMessage("新问题：" + question.getQuestionTitle() + " 等待回答", did, cid, qid, it.getUid());
        });
        return true;
    }

    @Transactional
    public boolean answer(int did, int cid, int qid, String content, String token) throws AuthException {
        loginService.hasAuth(token, Auth.TEACHER);
        int uid = loginService.getUid(token);
        questionRepo.answer(did, cid, qid, content, uid);
        QuestionWithSimpleUser question = questionRepo.findByDepartmentIdAndCourseIdAndQuestionId(did, cid, qid);
        messageService.addMessage("问题：" + question.getQuestionTitle() + "有新的回答", did, cid, qid, question.getQuestioner().getUid());
        return true;
    }
}
