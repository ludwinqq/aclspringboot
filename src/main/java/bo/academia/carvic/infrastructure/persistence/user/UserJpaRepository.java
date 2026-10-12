package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;
import bo.academia.carvic.application.user.dto.UserListSummaryResponse;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u.id AS id, u.username AS username, u.email AS email, u.status AS status, r.name AS roleName " +
        "FROM UserEntity u JOIN u.role r")
    Page<bo.academia.carvic.application.user.dto.UserProjection> fetchPagedUsers(Pageable pageable);


    @Query("SELECT new bo.academia.carvic.application.user.dto.UserListSummaryResponse(" +
        "u.id, u.username, u.email, r.name, u.status) " +
        "FROM UserEntity u " +
        "JOIN u.role r") // JOIN óptimo para extraer el nombre del rol en un solo viaje
    org.springframework.data.domain.Page<UserListSummaryResponse> fetchPagedUsersSummary(org.springframework.data.domain.Pageable pageable);


    /**
     * El Súper-JOIN Empresarial:
     * Cruza el Usuario, su Rol, los Permisos vinculados al Rol y evalúa si existen excepciones directas.
     * Mapea el resultado en caliente al DTO de aplicación usando 'new ...'
     */
    @Query ("SELECT new bo.academia.carvic.application.user.dto.AclMatrixRowResponse(" +
           "p.id, p.name, p.module, " +
           "CASE " +
           "  WHEN up.strategy IS NOT NULL THEN up.strategy " +
           "  ELSE (CASE WHEN rp.permitted = true THEN bo.academia.carvic.domain.user.AclStrategy.INHERITED_ALLOWED " +
           "             ELSE bo.academia.carvic.domain.user.AclStrategy.INHERITED_DENIED END) " +
           "END) " +
           "FROM UserEntity u " +
           "JOIN u.role r " +
           "JOIN RolePermissionEntity rp ON rp.role.id = r.id " +
           "JOIN PermissionEntity p ON p.id = rp.permission.id " +
           "LEFT JOIN UserPermissionEntity up ON up.user.id = u.id AND up.permission.id = p.id " +
           "WHERE u.id = :userId")
    List<AclMatrixRowResponse> fetchAclMatrixViaJoin(@Param("userId") UUID userId);
}
