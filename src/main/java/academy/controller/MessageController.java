package academy.controller;

import academy.dto.request.MessageDTO;
import academy.dto.request.Messages;
import academy.model.Follow;
import academy.model.Message;
import academy.model.Users;
import academy.service.follow.IFollowService;
import academy.service.message.IMessageService;
import academy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class MessageController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IFollowService followService;
    @Autowired
    private IUserService userService;
    @PostMapping("createMessage")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> createMes(@RequestBody MessageDTO messageDTO){
      Users users = userService.findById(messageDTO.getUserId()).get();
      Users friends= userService.findById(messageDTO.getFriendId()).get();
        List<Follow> followList = followService.getFollowsByUsers(users);
        for (Follow f: followList) {
            if (f.getFollow().getUserId() == messageDTO.getFriendId()){
                Message message = Message.builder()
                        .date(LocalDate.now())
                        .users(users)
                        .text(messageDTO.getText())
                        .friends(friends)
                        .status(true)
                        .build();
                messageService.save(message);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        Message message = Message.builder()
                .date(LocalDate.now())
                .users(users)
                .text(messageDTO.getText())
                .friends(friends)
                .status(false)
                .build();
        messageService.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/findMessage1vs1")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> findMessage1vs1(@RequestBody Messages messages){
        Users users = userService.findById(messages.getUserId()).get();
        Users friend = userService.findById(messages.getFriendId()).get();
        List<Message> messageList = messageService.findAllByUsersAndFriendsAndStatusTrue(users,friend);
        List<Message> messageList1 = messageService.findAllByUsersAndFriendsAndStatusTrue(friend,users);
        messageList.addAll(messageList1);
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if (o1.getId()>o2.getId()){
                    return 1;
                }else if (o1.getId()<o2.getId()){
                    return -1;
                }
                return 0;
            }
        });

        return new ResponseEntity<>(messageList,HttpStatus.OK);
    }

    @PostMapping("/findAllMessage/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> findAllMessage(@PathVariable Long id){
        Optional<Users> users = userService.findById(id);
        if(users.isPresent()){
            List<Message> messageList = messageService.findAllByUsers(users.get());
            return new ResponseEntity<>(messageList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteMess/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> deleteMess(@PathVariable Long id){
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
