package academy.controller;

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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class FollowController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IFollowService followService;
    @Autowired
    private IMessageService messageService;

    @PostMapping("/addFollow")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Follow> addFollow(@RequestBody Follow follow){
        Optional<Users> users = userService.findById(follow.getUsers().getUserId());
        Optional<Users> follows = userService.findById(follow.getFollow().getUserId());
        if (users.isPresent() && follows.isPresent()){
            follow.setDate(LocalDate.now());
            follow.setStatus(false);
            followService.save(follow);
            users.get().getFollowList().add(follow);
            userService.save(users.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Follow> confirm(@PathVariable Long id){
        Optional<Follow> follow = followService.findById(id);
        if (follow.isPresent()){
            follow.get().setDate(LocalDate.now());
            follow.get().setStatus(true);
            followService.save(follow.get());
            userService.save(follow.get().getUsers());
            Follow follow1 = Follow.builder()
                    .date(LocalDate.now())
                    .status(true)
                    .follow(follow.get().getUsers())
                    .users(follow.get().getFollow())
                    .build();
            follow.get().getFollow().getFollowList().add(follow1);
            followService.save(follow1);
            userService.save(follow.get().getFollow());

            List<Message> messageList = messageService.findAllByUsersAndFriends(follow1.getUsers(),follow1.getFollow());
            if (!messageList.isEmpty()){
                for (Message m: messageList) {
                    m.setStatus(true);
                    messageService.save(m);
                }
            }

            List<Message> messageList1 = messageService.findAllByUsersAndFriends(follow1.getFollow(),follow1.getUsers());
            if (!messageList.isEmpty()){
                for (Message m: messageList1) {
                    m.setStatus(true);
                    messageService.save(m);
                }
            }


            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/showFriend/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> show(@PathVariable Long id){
    List<Follow> followList = followService.getFollowsByUsers(userService.findById(id).get());
    return new ResponseEntity<>(followList,HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriend/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> deleteFriend(@PathVariable Long id){
        Optional<Follow> follow = followService.findById(id);
        if (follow.isPresent()){
           Follow follow1 = followService.searchByFollowAndUsers(follow.get().getUsers().getUserId(), follow.get().getFollow().getUserId());
            followService.delete(id);
            followService.delete(follow1.getFollowId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
