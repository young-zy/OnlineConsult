package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping(path = "/message/count")
    public ResponseEntity<Object> getUnreadMessageCount(@RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            int count = messageService.getUnreadMessageCount(token);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/message")
    public ResponseEntity<Object> getMessageList(@RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            int count = messageService.getUnreadMessageCount(token);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
