package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;

import bo.academia.carvic.application.common.AclQueryService;
import bo.academia.carvic.application.user.dto.UserProjection;
import bo.academia.carvic.domain.common.PagedResult;

@Service
public class FindAllUserUseCase {

    private final AclQueryService aclQueryService;

    public FindAllUserUseCase(AclQueryService aclQueryService) {
        this.aclQueryService = aclQueryService;
    }

    public PagedResult<UserProjection> execute(int page, int size) {
        return aclQueryService.getPagedUsers(page, size);
    }
}
