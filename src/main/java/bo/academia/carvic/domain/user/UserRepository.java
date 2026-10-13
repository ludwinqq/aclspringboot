package bo.academia.carvic.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User update(User user);
    PageResponse<User> findAllPaged(PageableRequest pageableRequest);
}
