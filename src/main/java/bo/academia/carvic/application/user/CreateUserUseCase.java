package bo.academia.carvic.application.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.user.dto.UserPermissionRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserPermissionRule;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class CreateUserUseCase {

    private UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public CreateUserUseCase(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

     public User execute(String username, String email, String password, List<UserPermissionRequest> reqs, java.util.UUID roleId) {
        User user = new User(username, email, password, null, roleId, null);

        if (reqs != null) {
            List<UserPermissionRule> rules = reqs.stream().map(r -> {
                Permission p = permissionRepository.findById(r.getPermissionId())
                        .orElseThrow(() -> new IllegalArgumentException("No existe permiso"));
                return new UserPermissionRule(p, r.getPermitted());
            }).collect(Collectors.toList());
            user.updatePermission(rules);
        }
        return userRepository.save(user);
    }
}
