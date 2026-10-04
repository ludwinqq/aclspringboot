package bo.academia.carvic.application.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id, User inputUser) {
        // Verificar que el usuario exista antes de actualizar
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar. El usuario con ID " + id + " no existe."));

        return userRepository.update(inputUser);
    }
}
