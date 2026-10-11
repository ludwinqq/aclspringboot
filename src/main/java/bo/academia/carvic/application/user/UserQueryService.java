package bo.academia.carvic.application.user;

import java.util.List;
import java.util.UUID;

import bo.academia.carvic.application.user.dto.AclMatrixRowResponse;
import bo.academia.carvic.application.user.dto.UserListSummaryResponse;
import bo.academia.carvic.domain.common.PagedResult;

public interface UserQueryService {

    // Al estar en aplicacion, se puede usar legítimamente el DTO de la aplicacion
    List<AclMatrixRowResponse> getUserAclMatrix(UUID userId);

    // NUEVO: Contrato para el listado masivo paginado
    PagedResult<UserListSummaryResponse> getPagedUsers(int page, int size);
}
