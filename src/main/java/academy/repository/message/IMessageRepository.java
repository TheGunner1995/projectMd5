package academy.repository.message;
import academy.model.Message;
import academy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message,Long> {

    List<Message> findAllByUsersAndFriendsAndStatusTrue(Users users, Users friend);
    List<Message> findAllByUsersAndFriends(Users users, Users friend);
    List<Message> findAllByUsers(Users users);
}
