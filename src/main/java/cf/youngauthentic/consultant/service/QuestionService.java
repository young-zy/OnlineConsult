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

    public List<QuestionEntity> getQuestions(int did, int cid, int page, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return questionRepo.findAllByDepartmentIdAndCourseId(did, cid, PageRequest.of(page, 10, Sort.by("createTime").descending()));
    }

}
