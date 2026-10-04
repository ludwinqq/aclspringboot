package bo.academia.carvic.application.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class FindByIdUserUseCase {

    private final UserRepository userRepository;

    public FindByIdUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe User"));
    }
}
