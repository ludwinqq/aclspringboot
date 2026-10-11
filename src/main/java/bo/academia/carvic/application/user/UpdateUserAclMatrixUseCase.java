package bo.academia.carvic.application.user;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.application.user.dto.UserPermissionRequest;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.user.AclStrategy;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.domain.user.UserRepository;


@Service 
public class UpdateUserAclMatrixUseCase {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    
    public UpdateUserAclMatrixUseCase(
        UserRepository userRepository, 
        PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public void execute(UUID userId, List<UserPermissionRequest> request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No existe usuario"));
        List<UserPermissionRule> explicitRules = request.stream()
                .filter(req -> req.getStrategy() == AclStrategy.ALLOWED || req.getStrategy() == AclStrategy.DENIED)
                .map(req -> {
                    var permission = permissionRepository.findById(req.getPermissionId())
                            .orElseThrow(() -> new IllegalArgumentException("Permiso no valido"));
                    return new UserPermissionRule(permission, req.getStrategy());
                }).toList();
        user.replaceAclRules(explicitRules);
        userRepository.update(user);
    }

}
