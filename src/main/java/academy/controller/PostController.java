package academy.controller;

import academy.model.Image;
import academy.model.Post;
import academy.service.image.IImageService;
import academy.service.post.IPostService;
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
public class PostController {
    @Autowired
    private IPostService postService;



    @GetMapping("/post")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<Post>> listPost(){
        List<Post> list = (List<Post>) postService.showPost();
        if (list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
    }

    @PostMapping("/createPost")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
//        List<Image> image = post.getImage();
//        imageService.saveAll(image);
        post.setDate(LocalDate.now());
        Post posts = postService.save(post);
        return new ResponseEntity<>(posts,HttpStatus.CREATED);
    }

    @PutMapping("/updatePost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> posts = postService.findById(id);
        if (posts.isPresent()){
            post.setPostId(id);
            postService.save(post);
            return new ResponseEntity<>(post,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Post> deletePost(@PathVariable Long id){
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()){
            post.get().setStatus(false);
            postService.save(post.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
