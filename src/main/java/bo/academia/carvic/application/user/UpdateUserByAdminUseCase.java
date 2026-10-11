package bo.academia.carvic.application.user;

import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service
public class UpdateUserByAdminUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserByAdminUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User execute(UUID id, String username, String email, UUID roleId, String refreshTokenHash, String newRawPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + id + " no existe"));

        // Modificamos datos de perfil manteniendo tu campo refreshTokenHash original
        user.changeProfileByAdmin(username, email, roleId, refreshTokenHash);

        // Si el administrador escribió una nueva clave, la encriptamos con BCrypt y forzamos el cambio
        if (newRawPassword != null && !newRawPassword.isBlank()) {
            String encryptedPassword = passwordEncoder.encode(newRawPassword);
            user.updatePassword(encryptedPassword, true);
        }

        return userRepository.update(user);
    }
}
