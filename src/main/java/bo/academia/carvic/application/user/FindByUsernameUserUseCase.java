package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class FindByUsernameUserUseCase {

    private final UserRepository userRepository;

    public FindByUsernameUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No existe username"));
    }
}
