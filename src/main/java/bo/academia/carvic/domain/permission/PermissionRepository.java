package bo.academia.carvic.domain.permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {

    Permission save(Permission permission);
    Permission update(Permission permission);
    List<Permission> findAll();
    Optional<Permission> findById(UUID id);
    Optional<Permission> findByName(String name);
    
}
