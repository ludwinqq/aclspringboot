package bo.academia.carvic.application.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import bo.academia.carvic.application.user.dto.UserPermissionRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.domain.user.UserRepository;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public UpdateUserUseCase(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public User execute(UUID id, String username, String email, String password, UUID roleId, List<UserPermissionRequest> permissionRequests) {
        // 1. Buscamos el usuario existente para garantizar la consistencia de auditoría
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + id + " no existe"));

        // 2. Modificamos las propiedades del negocio
        existingUser.setUsername(username);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setRoleId(roleId);

        // 3. Re-mapeamos la lista de excepciones si el frontend envía el arreglo
        if (permissionRequests != null) {
            List<UserPermissionRule> updatedRules = permissionRequests.stream()
                .map(req -> {
                    Permission permission = permissionRepository.findById(req.getPermissionId())
                        .orElseThrow(() -> new IllegalArgumentException("No existe el permiso con ID: " + req.getPermissionId()));
                    return new UserPermissionRule(permission, req.getPermitted());
                })
                .collect(Collectors.toList());

            existingUser.updatePermission(updatedRules);
        }

        // 4. Delegamos al método update() puro de tu repositorio de infraestructura
        return userRepository.update(existingUser);
    }
}
