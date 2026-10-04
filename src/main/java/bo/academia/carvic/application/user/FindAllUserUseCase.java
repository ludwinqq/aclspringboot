package bo.academia.carvic.application.user;

import java.util.List;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class FindAllUserUseCase {

    private final UserRepository userRepository;

    public FindAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }
}
