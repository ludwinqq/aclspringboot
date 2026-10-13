package bo.academia.carvic.application.user;

import org.springframework.stereotype.Service;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;

@Service 
public class GetPagedUsersUseCase {

    private final UserRepository userRepository;

    public GetPagedUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PageResponse<User> execute(int page, int size, String sortBy, String direction) {
        // 1. Creamos el objeto de petición de paginación agnóstico
        PageableRequest request = new PageableRequest(page, size, sortBy, direction);
        // 2. Invocamos al puerto del repositorio del dominio, que devuelve entidades puras envueltas en PageResponse
        return userRepository.findAllPaged(request);
    }
}
