package academy.sercurity.userPrincipal;

import academy.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private Long userId;
    private String fullName;
    private String userName;
    private int age;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;

    private Collection<? extends GrantedAuthority> roles;
    public static UserPrincipal build(Users user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
        return UserPrincipal.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .age(user.getAge())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(grantedAuthorities)
                .build();
    }


        @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
