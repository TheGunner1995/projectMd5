package academy.service.message;

import academy.model.Message;
import academy.model.Users;
import academy.service.IGenericService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMessageService extends IGenericService<Message, Long> {

    List<Message> findAllByUsersAndFriendsAndStatusTrue(Users users, Users friend);
    List<Message> findAllByUsersAndFriends(Users users, Users friend);
    List<Message> findAllByUsers(Users users);
}
