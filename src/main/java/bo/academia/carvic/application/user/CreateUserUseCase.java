package bo.academia.carvic.application.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.application.user.dto.UserPermissionRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.user.AclStrategy;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional  
    public User execute(String username, String email, String password, List<UserPermissionRequest> reqs, UUID roleId) {
        //1. Encriptamos la contraseña de forma real con Bcrypt antes de que toque el domino
        String encryptedPassword = passwordEncoder.encode(password);
        // 2. Inicializamos de manera segura la bandera "refreshTokenHash". 
        // Como es usuario nuevo por defecto con ACTIVE
        String defaultRefreshTokenHash = "ACTIVE";
        // 3. Instanciamos el objeto utilizando el constructor de creación de tu dominio.
        // Esto autovalida que el username, email y password no viajen corruptos o vacíos.
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setRoleId(roleId);
        user.setRefreshTokenHash(defaultRefreshTokenHash);
        // 4. Si el administrador pre-configuró excepciones explícitas desde la grilla al crearlo:
        if ( reqs != null ) {
            List<UserPermissionRule> rules = reqs.stream()
                    .filter(r -> r.getStrategy() == AclStrategy.ALLOWED || r.getStrategy() == AclStrategy.DENIED)
                    .map(r -> {
                        Permission p = permissionRepository.findById(r.getPermissionId()).orElseThrow(() -> new IllegalArgumentException("No existe el permiso con ID: " + r.getPermissionId()));
                        // Construimos la regla con el enumerador de la matriz empresarial
                        return new UserPermissionRule(p, r.getStrategy());
                    })
                    .collect(Collectors.toList());
            user.updatePermission(rules);
        }
        return userRepository.save(user);
    }
}
