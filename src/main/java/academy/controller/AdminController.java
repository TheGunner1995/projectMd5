package academy.controller;

import academy.model.Users;
import academy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AdminController {
    @Autowired
    private IUserService userService;
    @GetMapping("/showListUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> showList(){
        List<Users> usersList = (List<Users>) userService.findAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> block(@PathVariable Long id){
        Optional<Users> users = userService.findById(id);
        if (users.isPresent()){
            if (users.get().getRoles().size()==2){
                return new ResponseEntity<>("you can't block this account",HttpStatus.NOT_FOUND);
            }else {
                users.get().setStatus(!users.get().isStatus());
                userService.save(users.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
