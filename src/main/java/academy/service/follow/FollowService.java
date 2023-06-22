package academy.service.follow;

import academy.model.Follow;
import academy.model.Users;
import academy.repository.follow.IFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FollowService implements IFollowService{
    @Autowired
    private IFollowRepository followRepository;
    @Override
    public Iterable<Follow> findAll() {
        return followRepository.findAll();
    }
    @Override
    public Optional<Follow> findById(Long id) {
        return followRepository.findById(id);
    }
    @Override
    public Follow save(Follow follow) {
        return followRepository.save(follow);
    }
    @Override
    public void delete(Long id) {
        followRepository.deleteById(id);
    }
    @Override
    public List<Follow> getFollowsByUsers(Users users) {
        return followRepository.getFollowsByUsers(users);
    }
    @Override
    public Follow searchByFollowAndUsers(Long follow, Long users) {
        return followRepository.searchByFollowAndUsers(follow,users);
    }
}
