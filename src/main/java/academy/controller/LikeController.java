package academy.controller;

import academy.model.Like;
import academy.model.Post;
import academy.model.Users;
import academy.service.like.ILikeService;
import academy.service.post.IPostService;
import academy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class LikeController {
    @Autowired
    private IPostService postService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ILikeService likeService;

    @PostMapping("/addLike")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Like> addLike(@RequestBody Like like) {
        Optional<Users> users = userService.findById(like.getUsers().getUserId());
        Optional<Post> posts = postService.findById(like.getPost().getPostId());
        Users user = new Users();
        Post post = new Post();
        if (users.isPresent()){
            user = users.get();
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (posts.isPresent()){
            post = posts.get();
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Like> likes = likeService.findByPostAndUsers(post,user);
        Like like1 = new Like();
        if (likes.isPresent()){
            like1 = likes.get();
            like1.setStatus(!like1.isStatus());
            likeService.save(like1);
            if (!like1.isStatus()){
                post.setLike(post.getLike()-1);
            }else {
                post.setLike(post.getLike()+1);
            }
            postService.save(post);
        }else {
           Like like2 = Like.builder()
                    .post(post)
                    .users(user)
                    .status(true)
        .build();
           likeService.save(like2);
           post.setLike(post.getLike()+1);
           postService.save(post);
        }
        return new ResponseEntity<>(HttpStatus.OK);
        }
    }
