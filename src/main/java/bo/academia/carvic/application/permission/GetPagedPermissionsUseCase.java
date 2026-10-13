package bo.academia.carvic.application.permission;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;

@Service 
public class GetPagedPermissionsUseCase {

    private final PermissionRepository permissionRepository;

    public GetPagedPermissionsUseCase(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PageResponse<Permission> execute(int page, int size, String sortBy, String direction) {
        //1. Creamos el objeto de peticion de paginacion agnostico
        PageableRequest request = new PageableRequest(page, size, sortBy, direction);
        //2. Invocamos al puerto del repositorio del dominio que devuelve entidades puras envueltas en PageResponse
        return permissionRepository.findAllPaged(request);
    }
}
