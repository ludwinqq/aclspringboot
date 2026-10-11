package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;
import bo.academia.carvic.application.user.dto.UserListSummaryResponse;
import bo.academia.carvic.domain.common.PagedResult;

@Service
public class FindAllUserUseCase {

    private final UserQueryService userQueryService;

    public FindAllUserUseCase(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    public PagedResult<UserListSummaryResponse> execute(int page, int size) {
        // Ejecuta el flujo de lectura controlado
        return userQueryService.getPagedUsers(page, size);
    }
}
