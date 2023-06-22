package academy.repository.follow;

import academy.model.Follow;
import academy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> getFollowsByUsers(Users users);
    @Query("SELECT f FROM Follow f WHERE f.follow.userId = :follow AND f.users.userId = :users")
    Follow searchByFollowAndUsers(@Param("follow") Long follow, @Param("users") Long users);
}
