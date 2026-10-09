package bo.academia.carvic.application.user;

import java.util.List;
import org.springframework.stereotype.Service;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service
public class FindAllUserUseCase {

    private final UserRepository repository;

    public FindAllUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> execute() {
        return repository.findAll();
    }
}
