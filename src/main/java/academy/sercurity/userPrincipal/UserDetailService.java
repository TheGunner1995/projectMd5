package academy.sercurity.userPrincipal;


import academy.model.Users;
import academy.service.user.IUserService;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Users user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed -> NOT FOUND USE at username: " + username));

        return UserPrincipal.build(user);
    }
}
