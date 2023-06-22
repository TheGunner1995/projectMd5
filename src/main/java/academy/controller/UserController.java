package academy.controller;

import academy.dto.request.ChangePass;
import academy.model.Users;
import academy.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor

public class UserController {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;


    @GetMapping("/search/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        List<Users> usersList = (List<Users>) userService.searchByUserName(name);
        if(usersList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }
    }
    @PostMapping("/changePass/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> changePass(@PathVariable Long id, @RequestBody ChangePass pass){
        Optional<Users> users = userService.findById(id);
        if (users.isPresent()){
            if (passwordEncoder.matches(pass.getPassword(), users.get().getPassword())){
                users.get().setPassword(passwordEncoder.encode(pass.getNewPass()));
                userService.save(users.get());
                return new ResponseEntity<>("Change password successfully", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
