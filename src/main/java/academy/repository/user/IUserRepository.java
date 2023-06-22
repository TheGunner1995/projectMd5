package academy.repository.user;

import academy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {

    boolean existsByUserName(String username);
    boolean existsByPhone(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<Users> findByUserName(String username);

    @Query("select u from Users u where u.userName like %:name%")
    Iterable<Users> searchByUserName(@Param("name") String name);
}
