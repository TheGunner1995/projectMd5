package academy.controller;

import academy.dto.request.SignInForm;
import academy.dto.request.SignUpForm;
import academy.dto.response.JwtResponse;
import academy.dto.response.ResponseMessage;
import academy.model.RoleName;
import academy.model.Roles;
import academy.model.Users;
import academy.sercurity.jwt.JwtProvider;
import academy.sercurity.userPrincipal.UserPrincipal;
import academy.service.mail.SendEmailService;
import academy.service.role.IRoleService;
import academy.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")

    public class AuthController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
     private final SendEmailService sendEmailService;


    @PostMapping("/signUp")
    public ResponseEntity<ResponseMessage> doSignUp(@Valid @RequestBody SignUpForm signUpForm,BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors =bindingResult.getFieldErrors();
            StringBuilder stringBuilder=new StringBuilder();
            for (FieldError field:fieldErrors) {
                stringBuilder.append(field.getField())
                        .append(" : ")
                        .append(field.getDefaultMessage())
                        .append(" ; ");
            }
            return ResponseEntity.badRequest().body(
                    new ResponseMessage("Failed","Validation",stringBuilder.toString())
            );
        }


        boolean isExistUsername = userService.existsByUsername(signUpForm.getUsername());
        if (isExistUsername) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This username is already existed!")
                            .data("")
                            .build()
            );
        }

        boolean isExistPhoneNumber = userService.existsByPhoneNumber(signUpForm.getPhoneNumber());
        if (isExistPhoneNumber) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This phone number is already existed!")
                            .data("")
                            .build()
            );
        }
        boolean isExistEmail = userService.existsByEmail(signUpForm.getEmail());
        if (isExistEmail) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This email is already existed!")
                            .data("")
                            .build()
            );
        }


        Set<Roles> roles = new HashSet<>();

        if (signUpForm.getRoles() == null || signUpForm.getRoles().isEmpty()) {
            Roles role = roleService.findByName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
            roles.add(role);
        } else {
            signUpForm.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(adminRole);
                    case "user":
                        Roles userRole = roleService.findByName(RoleName.USER)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(userRole);
                }
            });
        }

        Users user = Users.builder()
                .fullName(signUpForm.getFullName())
                .userName(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .phone(signUpForm.getPhoneNumber())
                .email(signUpForm.getEmail())
                .age(signUpForm.getAge())
                .roles(roles)
                .status(true)
                .build();

        Users users = userService.save(user);
        String html = "<b>Xin chào </b>"+ users.getUserName() + "<br>" + "<b>chúc mừng bạn đã đăng ký thành công</b>"+ "<br>"
                + "<b>Tài khoản của bạn là : </b>"+ users.getUserName() + "<br>" + "<b> Mật khẩu : </b>" + signUpForm.getPassword();
        sendEmailService.senMail(users.getEmail(), "wellcome", html);

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Account created successfully!")
                        .data(users)
                        .build()
        );
    }


    @PostMapping("/signIn")
    public ResponseEntity<?> doSignIn(@RequestBody SignInForm signInForm) {
        Users users = userService.findByUsername(signInForm.getUsername()).get();
        if (!users.isStatus()) {
            return new ResponseEntity<>("account locked! please contact to admin", HttpStatus.NOT_FOUND);
        } else {
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                signInForm.getUsername(),
                                signInForm.getPassword())
                        );

                String token = jwtProvider.generateToken(authentication);
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                return new ResponseEntity<>(
                        JwtResponse.builder()
                                .status("OK")
                                .type("Bearer")
                                .fullName(userPrincipal.getFullName())
                                .token(token)
                                .roles(userPrincipal.getAuthorities())
                                .build(), HttpStatus.OK);

            } catch (AuthenticationException e) {
                return new ResponseEntity<>(
                        ResponseMessage.builder()
                                .status("Failed")
                                .message("Invalid username or password!")
                                .data("")
                                .build(), HttpStatus.UNAUTHORIZED);
            }


        }
    }

}
