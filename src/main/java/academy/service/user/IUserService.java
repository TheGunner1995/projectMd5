package academy.service.user;

import academy.model.Users;
import academy.service.IGenericService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGenericService<Users, Long> {
    List<Users> findAllUser();

    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<Users> findByUsername(String username);
    Iterable<Users> searchByUserName(@Param("name") String name);

}
