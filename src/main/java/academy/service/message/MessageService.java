package academy.service.message;

import academy.model.Message;
import academy.model.Users;
import academy.repository.message.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MessageService implements IMessageService{
    @Autowired
    private IMessageRepository messageRepository;
    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }



    @Override
    public List<Message> findAllByUsersAndFriendsAndStatusTrue(Users users, Users friend) {
        return messageRepository.findAllByUsersAndFriendsAndStatusTrue(users, friend);
    }

    @Override
    public List<Message> findAllByUsersAndFriends(Users users, Users friend) {
        return messageRepository.findAllByUsersAndFriends(users, friend);
    }

    @Override
    public List<Message> findAllByUsers(Users users) {
        return messageRepository.findAllByUsers(users);
    }
}
