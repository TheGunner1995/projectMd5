package academy.service.user;

import academy.model.Users;
import academy.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final IUserRepository userRepository;
    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Users> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhone(phoneNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Iterable<Users> searchByUserName(String name) {
        return userRepository.searchByUserName(name);
    }
}
