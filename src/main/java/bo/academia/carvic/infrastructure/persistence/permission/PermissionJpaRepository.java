package bo.academia.carvic.infrastructure.persistence.permission;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import bo.academia.carvic.application.permission.dto.PermissionProjection;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID>{
    Optional<PermissionEntity> findByName(String name);
    Page<PermissionProjection> findBy(Pageable pageable);
}
