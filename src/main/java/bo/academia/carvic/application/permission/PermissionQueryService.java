package bo.academia.carvic.application.permission;

import bo.academia.carvic.application.permission.dto.PermissionListResponse;
import bo.academia.carvic.domain.common.PagedResult;

public interface PermissionQueryService {

    PagedResult<PermissionListResponse> getPagedPermissions(int page, int size);

}
