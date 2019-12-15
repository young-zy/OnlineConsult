package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.QuestionRequestModel;
import cf.youngauthentic.consultant.model.ResponseModel;
import cf.youngauthentic.consultant.model.question.QuestionEntity;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/department/{did}/course/{cid}/question")
    public ResponseEntity<Object> getQuestions(@PathVariable int did,
                                               @PathVariable int cid,
                                               @RequestHeader(value = "token", defaultValue = "") String token,
                                               @RequestParam(defaultValue = "1") String pageStr) {
        try {
            int page = Integer.parseInt(pageStr);
            List<QuestionEntity> questions = questionService.getQuestions(did, cid, --page, token);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/department/{did}/course/{cid}/question/{qid}")
    public ResponseEntity<Object> getQuestion(@PathVariable int did,
                                              @PathVariable int cid,
                                              @PathVariable int qid,
                                              @RequestHeader(value = "token", defaultValue = "") String token
    ) {
        try {
            QuestionEntity question = questionService.getQuestion(did, cid, qid, token);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/department/{did}/course/{cid}/question")
    public ResponseEntity<Object> questionPost(@PathVariable int did,
                                               @PathVariable int cid,
                                               @RequestBody QuestionRequestModel question,
                                               @RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            questionService.saveQuestion(did, cid, question.getTitle(), question.getContent(), token);
            return new ResponseEntity<>(new ResponseModel(true, ""), HttpStatus.ACCEPTED);
        } catch (AuthException e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModel(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
