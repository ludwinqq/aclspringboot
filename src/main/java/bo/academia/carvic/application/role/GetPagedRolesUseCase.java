package bo.academia.carvic.application.role;
import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Service 
public class GetPagedRolesUseCase {
    
    private final RoleRepository roleRepository;

    public GetPagedRolesUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public PageResponse<Role> execute(int page, int size, String sortBy, String direction) {
        // 1. Creamos el objeto de petición de paginación agnóstico
        PageableRequest request = new PageableRequest(page, size, sortBy, direction);

        // 2. Invocamos al puerto del repositorio del dominio, que devuelve entidades puras envueltas en PageResponse
        return roleRepository.findAllPaged(request);
    }
}
