package academy.service.like;

import academy.model.Like;
import academy.model.Post;
import academy.model.Users;
import academy.repository.like.ILikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LikeService implements ILikeService{
    @Autowired
    private ILikeRepository likeRepository;
    @Override
    public Iterable<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Optional<Like> findById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void delete(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Optional<Like> findByPostAndUsers(Post post, Users users) {
        return likeRepository.findByPostAndUsers(post,users);
    }
}
