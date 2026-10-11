package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.UUID;

// CORREGIDO: Añadidos los imports de Spring Data obligatorios para esta capa de infraestructura
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.application.user.UserQueryService;
import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;
import bo.academia.carvic.application.user.dto.UserListSummaryResponse;
import bo.academia.carvic.domain.common.PagedResult;
import bo.academia.carvic.infrastructure.common.libraries.PaginationLibrary;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserJpaRepository repository;
    private final PaginationLibrary paginationLibrary;

    public UserQueryServiceImpl(UserJpaRepository repository, PaginationLibrary paginationLibrary) {
        this.repository = repository;
        this.paginationLibrary = paginationLibrary;
    }

    @Override
    @Transactional(readOnly = true) // Optimización de lectura masiva de base de datos
    public List<AclMatrixRowResponse> getUserAclMatrix(UUID userId) {
        // Validamos la existencia rápido antes del query
        if (!repository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
        }
        
        // Ejecuta el query con los múltiples JOINs que calcula la BD en milisegundos
        return repository.fetchAclMatrixViaJoin(userId);
    }

    // CORREGIDO: Se agregó la anotación @Override explícita para cumplir el contrato de la interfaz
    @Override
    @Transactional(readOnly = true)
    public PagedResult<UserListSummaryResponse> getPagedUsers(int page, int size) {
        // Ahora el compilador encuentra perfectamente 'Pageable' gracias al import de arriba
        Pageable pageable = PageRequest.of(page, size);
        
        // El compilador encuentra perfectamente 'Page' gracias al import de arriba
        Page<UserListSummaryResponse> springPage = repository.fetchPagedUsersSummary(pageable);
        
        // Usamos tu nueva librería cargada para resolver el mapeo en una sola línea elegante 🚀
        return paginationLibrary.toPagedResult(springPage);
    }
}
