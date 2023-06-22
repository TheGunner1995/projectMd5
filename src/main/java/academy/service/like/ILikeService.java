package academy.service.like;

import academy.model.Like;
import academy.model.Post;
import academy.model.Users;
import academy.service.IGenericService;

import java.util.Optional;

public interface ILikeService extends IGenericService<Like, Long> {
    Optional<Like> findByPostAndUsers(Post post, Users users);

}
