package academy.repository.like;

import academy.model.Like;
import academy.model.Post;
import academy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByPostAndUsers(Post post, Users users);
}
