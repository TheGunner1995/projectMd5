package academy.repository.post;

import academy.model.Post;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p from Post p where p.status = true ")
    Iterable<Post> showPost();
}
