package bo.academia.carvic.application.user;

import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service
public class UpdateUserPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inyección real de Spring Security

    public UpdateUserPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void execute(UUID userId, String currentRawPassword, String newRawPassword, String confirmNewRawPassword) {
        // 1. Validar que la nueva contraseña y su repetición coincidan en texto plano
        if (newRawPassword == null || !newRawPassword.equals(confirmNewRawPassword)) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden");
        }

        // 2. Buscamos al usuario en la base de datos
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 3. CORRECCIÓN REAL: passwordEncoder.matches toma (TextoPlano, HashDeBaseDatos)
        // Si no coinciden, lanza la excepción inmediatamente.
        if (!passwordEncoder.matches(currentRawPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // 4. Encriptamos la nueva contraseña usando BCrypt de forma real
        String newEncryptedPassword = passwordEncoder.encode(newRawPassword);

        // 5. Enviamos el hash real al dominio y desactivamos la obligación de cambio
        user.updatePassword(newEncryptedPassword, false);

        // 6. Guardamos los cambios
        userRepository.update(user);
    }
}
