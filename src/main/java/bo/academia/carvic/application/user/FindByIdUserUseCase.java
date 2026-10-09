package bo.academia.carvic.application.user;

import java.util.UUID;
import org.springframework.stereotype.Service;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service
public class FindByIdUserUseCase {

    private final UserRepository repository;

    public FindByIdUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public User execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }
}
