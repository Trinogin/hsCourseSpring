package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entity.Usr;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Usr> registerUser(Usr user) {
        user.setActive(true);
        user.setRoles("ROLE_USER");
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}
