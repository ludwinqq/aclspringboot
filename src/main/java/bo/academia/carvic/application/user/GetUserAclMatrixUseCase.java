package bo.academia.carvic.application.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;
import bo.academia.carvic.domain.user.AclStrategy;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.domain.user.UserRepository;
import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;

@Service
public class GetUserAclMatrixUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final UserQueryService userQueryService;

    public GetUserAclMatrixUseCase(
        UserRepository userRepository, 
        RoleRepository roleRepository, 
        PermissionRepository permissionRepository, 
        UserQueryService userQueryService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userQueryService = userQueryService;
    }

    public List<AclMatrixRowResponse> execute(UUID userId) {
        // Ejecuta la consulta optimizada de lectura
        return userQueryService.getUserAclMatrix(userId);
    }

    /*@Transactional(readOnly = true)
    public List<AclMatrixRowResponse> execute(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario inexistente"));
        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Rol inexistente"));
        List<Permission> allPermissions = permissionRepository.findAll();

        return allPermissions.stream().map(permission -> {
            // 1. Ver si el Rol lo tiene permitido
            boolean roleHasIt = role.hasAccess(permission.getName());
            
            // 2. Ver si el usuario tiene una excepción explícita guardada
            Optional<UserPermissionRule> userCustomRule = user.getPermissionRules().stream()
                    .filter(rule -> rule.getPermission().getId().equals(permission.getId()))
                    .findFirst();

            AclStrategy calculatedStrategy;
            if (userCustomRule.isPresent()) {
                // Si hay excepción directa, toma el valor guardado (ALLOWED o DENIED)
                calculatedStrategy = userCustomRule.get().getStrategy();
            } else {
                // Si no hay excepción, se calcula su herencia reflexiva para que el select del frontend se pinte solo
                calculatedStrategy = roleHasIt ? AclStrategy.INHERITED_ALLOWED : AclStrategy.INHERITED_DENIED;
            }

            return new AclMatrixRowResponse(
                    permission.getId(),
                    permission.getName(),
                    permission.getModule(),
                    calculatedStrategy
            );
        }).toList();
    }*/
}
