package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class FindByEmailUserUseCase {

    private final UserRepository userRepository;

    public FindByEmailUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("No existe correo"));
    }
}
