package bo.academia.carvic.infrastructure.persistence.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.application.permission.PermissionQueryService;
import bo.academia.carvic.application.permission.dto.PermissionListResponse;
import bo.academia.carvic.domain.common.PagedResult;
import bo.academia.carvic.infrastructure.common.libraries.PaginationLibrary;

@Service
public class PermissionQueryServiceImpl implements PermissionQueryService {

    private final PermissionJpaRepository repository;
    private final PaginationLibrary paginationLibrary; // Cargamos tu librería de paginación

    public PermissionQueryServiceImpl(PermissionJpaRepository repository, PaginationLibrary paginationLibrary) {
        this.repository = repository;
        this.paginationLibrary = paginationLibrary;
    }

    // ¡AQUÍ ESTÁ EL CÓDIGO REAL DEL MÉTODO!
    @Override
    @Transactional(readOnly = true) // Optimiza la lectura en la base de datos
    public PagedResult<PermissionListResponse> getPagedPermissions(int page, int size) {
        // 1. Configuramos el tamaño y número de página para Spring
        Pageable pageable = PageRequest.of(page, size);
        
        // 2. Ejecutamos la consulta en la base de datos
        Page<PermissionListResponse> springPage = repository.fetchPagedPermissions(pageable);
        
        // 3. Usamos tu librería para traducir el resultado al vuelo en una sola línea 🚀
        return paginationLibrary.toPagedResult(springPage);
    }
}
