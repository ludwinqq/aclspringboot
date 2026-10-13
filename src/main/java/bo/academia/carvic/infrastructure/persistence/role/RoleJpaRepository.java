package bo.academia.carvic.infrastructure.persistence.role;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByName(String name);
    <T> Page<T> findBy(Pageable pageable, Class<T> type);
    //Page<RoleEntity> findAllPaged(Pageable pageable);
}
