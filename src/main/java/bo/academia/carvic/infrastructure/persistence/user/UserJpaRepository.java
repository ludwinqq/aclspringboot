package bo.academia.carvic.infrastructure.persistence.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    
}
