package bo.academia.carvic.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.application.common.AclQueryService;
import bo.academia.carvic.application.permission.dto.PermissionProjection;
import bo.academia.carvic.application.role.dto.RoleProjection;
import bo.academia.carvic.application.user.dto.UserProjection;
import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;
import bo.academia.carvic.domain.common.PagedResult;
import bo.academia.carvic.infrastructure.common.libraries.PaginationLibrary;
import bo.academia.carvic.infrastructure.persistence.permission.PermissionJpaRepository;
import bo.academia.carvic.infrastructure.persistence.role.RoleJpaRepository;
import bo.academia.carvic.infrastructure.persistence.user.UserJpaRepository;

@Service
public class AclQueryServiceImpl implements AclQueryService {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PermissionJpaRepository permissionJpaRepository;
    private final PaginationLibrary paginationLibrary; // Tu librería cargada al estilo CodeIgniter

    public AclQueryServiceImpl(
            UserJpaRepository userJpaRepository, 
            PermissionJpaRepository permissionJpaRepository, 
            PaginationLibrary paginationLibrary, 
            RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.permissionJpaRepository = permissionJpaRepository;
        this.paginationLibrary = paginationLibrary;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<UserProjection> getPagedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserProjection> springPage = userJpaRepository.fetchPagedUsers(pageable, UserProjection.class);
        return paginationLibrary.toPagedResult(springPage); // Paginación en 1 línea
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<PermissionProjection> getPagedPermissions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PermissionProjection> springPage = permissionJpaRepository.findBy(pageable, PermissionProjection.class);
        return paginationLibrary.toPagedResult(springPage); // Reutilización directa de tu librería
    }

    @Override
    @Transactional(readOnly = true)
    public List<AclMatrixRowResponse> getUserAclMatrix(UUID userId) {
        if (!userJpaRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        return userJpaRepository.fetchAclMatrixViaJoin(userId);
    }

    @Override
    @Transactional (readOnly = true)
    public PagedResult<RoleProjection> getPagedRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleProjection> sprinPage = roleJpaRepository.findBy(pageable, RoleProjection.class);
        throw new UnsupportedOperationException("Unimplemented method 'getPagedRoles'");
    }
}
