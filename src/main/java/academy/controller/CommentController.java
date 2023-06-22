package academy.controller;
import academy.model.Comment;
import academy.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @PostMapping("/createComment")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        comment.setDate(LocalDate.now());
        return ResponseEntity.ok(commentService.save(comment));
    }

    @DeleteMapping("/deleteComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id){
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()){
            commentService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment){
        Optional<Comment> comments = commentService.findById(id);
        if (comments.isPresent()){
            comment.setCommentId(id);
            comment.setDate(LocalDate.now());
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
