package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.role.RoleRepository;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class CreateUserUseCase {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public CreateUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User execute(User inputUser) {
        roleRepository.findById(inputUser.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No existe rol"));
        userRepository.findByEmail(inputUser.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Ya existe este correp");
        });
        userRepository.findByUsername(inputUser.getUsername()).ifPresent(u-> {
            throw new IllegalArgumentException("Ya existe este usuario");
        });

        return userRepository.save(inputUser);
        
    }
}
