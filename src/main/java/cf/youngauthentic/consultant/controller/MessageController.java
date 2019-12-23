package cf.youngauthentic.consultant.controller;

import cf.youngauthentic.consultant.model.message.SimpleMessage;
import cf.youngauthentic.consultant.service.AuthException;
import cf.youngauthentic.consultant.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    final
    MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/message/count/unread")
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
    public ResponseEntity<Object> getMessageList(@RequestHeader(value = "token", defaultValue = "") String token,
                                                 @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        try {
            int page = Integer.parseInt(pageStr);
            List<SimpleMessage> messages = messageService.getMessages(token, --page);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/message/count")
    public ResponseEntity<Object> getMessageCount(@RequestHeader(value = "token", defaultValue = "") String token) {
        try {
            int count = messageService.getMessageCount(token);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/message/{mid}")
    public ResponseEntity<Object> getMessage(@RequestHeader(value = "token", defaultValue = "") String token,
                                             @PathVariable int mid
    ) {
        try {
            return new ResponseEntity<>(messageService.getMessage(mid, token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
