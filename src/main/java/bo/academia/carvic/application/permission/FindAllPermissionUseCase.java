package bo.academia.carvic.application.permission;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.common.AclQueryService;
import bo.academia.carvic.application.permission.dto.PermissionProjection;
import bo.academia.carvic.domain.common.PagedResult;

@Service 
public class FindAllPermissionUseCase {

    private final AclQueryService aclQueryService;

    public FindAllPermissionUseCase(AclQueryService aclQueryService) {
        this.aclQueryService = aclQueryService;
    }

    public PagedResult<PermissionProjection> execute(int page, int size) {
        return aclQueryService.getPagedPermissions(page, size);
    }
}
