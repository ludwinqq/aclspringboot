package bo.academia.carvic.infrastructure.persistence.permission;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bo.academia.carvic.application.permission.dto.PermissionListResponse;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID>{
    Optional<PermissionEntity> findByName(String name);

    @Query("SELECT new bo.academia.carvic.application.permission.dto.PermissionListResponse(" +
           "p.id, p.name, p.title, p.module) " +
           "FROM PermissionEntity p")
    Page<PermissionListResponse> fetchPagedPermissions(Pageable pageable);
}
