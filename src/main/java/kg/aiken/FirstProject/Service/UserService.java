package kg.aiken.FirstProject.Service;

import kg.aiken.FirstProject.Entity.User;
import kg.aiken.FirstProject.Exception.UserEmailAlreadyExistException;
import kg.aiken.FirstProject.Exception.UserNotFoundException;
import kg.aiken.FirstProject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistException();
        }
        user.setCreatedAt(LocalDateTime.now());
        return repository.save(user);
    }


    public Page<User> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }


}
