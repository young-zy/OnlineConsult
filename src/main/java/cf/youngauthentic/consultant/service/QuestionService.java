package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.QuestionEntity;
import cf.youngauthentic.consultant.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    public List<QuestionEntity> getQuestions(int did, int cid, int page, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.findAllByDepartmentIdAndCourseId(did, cid, PageRequest.of(page, 10, Sort.by("createTime").descending()));
    }

    public QuestionEntity getQuestion(int did, int cid, int qid, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.findByDepartmentIdAndCourseIdAndQuestionId(did, cid, qid);
    }

    /**
     * @param did     departmentId
     * @param cid     courseId
     * @param title   questionTitle
     * @param content questionContent
     * @return isSuccessful
     * @throws AuthException when has no auth
     */

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
        return true;
    }
}
