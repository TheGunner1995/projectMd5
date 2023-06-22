package academy.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {
    @Size(min = 6, message = "Username more than 6 characters")
    private String username;
    @Size(min = 6,message = "Password more than 6 characters")

    private String password;
    @Size(min = 10,message = "Full name more than 10 characters")
    private String fullName;
    private String email;
    private int age;
    private String phoneNumber;
    private Set<String> roles;

}
