package academy.service.follow;

import academy.model.Follow;
import academy.model.Users;
import academy.service.IGenericService;

import java.util.List;

public interface IFollowService extends IGenericService<Follow, Long> {
    List<Follow> getFollowsByUsers(Users users);
    Follow searchByFollowAndUsers(Long follow, Long users);


}
