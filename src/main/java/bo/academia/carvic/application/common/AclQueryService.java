package bo.academia.carvic.application.common;

import java.util.List;
import java.util.UUID;

import bo.academia.carvic.application.permission.dto.PermissionProjection;
import bo.academia.carvic.application.role.dto.RoleProjection;
import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;
import bo.academia.carvic.application.user.dto.UserProjection;
import bo.academia.carvic.domain.common.PagedResult;

public interface AclQueryService {

    PagedResult<UserProjection> getPagedUsers(int page, int size);
    PagedResult<PermissionProjection> getPagedPermissions(int page, int size);
    PagedResult<RoleProjection> getPagedRoles(int page, int size);
    List<AclMatrixRowResponse> getUserAclMatrix(UUID userId); // Mantenemos la consulta de la matriz aquí

}
