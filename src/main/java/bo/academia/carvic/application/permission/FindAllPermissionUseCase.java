package bo.academia.carvic.application.permission;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.permission.dto.PermissionListResponse;
import bo.academia.carvic.domain.common.PagedResult;

@Service 
public class FindAllPermissionUseCase {

    private final PermissionQueryService permissionQueryService;

    public FindAllPermissionUseCase(PermissionQueryService permissionQueryService) {
        this.permissionQueryService = permissionQueryService;
    }

    public PagedResult<PermissionListResponse> execute(int page, int size) {
        return permissionQueryService.getPagedPermissions(page, size);
    }
}
