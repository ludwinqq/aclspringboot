package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleJpaRepository;

@Repository 
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public User save(User user) {
        RoleEntity roleEntity = roleJpaRepository.findById(user.getRoleId()).orElseThrow(()-> new IllegalArgumentException("No existe el rol"));
        UserEntity userEntity = new UserEntity();

        // Si el dominio ya trae un ID asignado, se lo pasamos a la entidad
        if ( user.getId() != null ) {
            userEntity.setId(user.getId());
        }

        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRefreshTokenHash(user.getRefreshTokenHash());
        userEntity.setRequirePasswordChange(user.getRequirePasswordChange());
        userEntity.setRole(roleEntity);

        if ( user.getStatus() != null ) {
            userEntity.setStatus(user.getStatus());
        }

        UserEntity saved = userJpaRepository.save(userEntity);

        return mapToDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
        
        if ( entity == null ) {
            return null;
        }

        UUID roleId = ( entity.getRole() != null ) ? entity.getRole().getId(): null;

        User domain = new User();
        domain.setId(entity.getId());
        domain.setUsername(entity.getUsername());
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        domain.setRefreshTokenHash(entity.getRefreshTokenHash());
        domain.setRequirePasswordChange(entity.getRequirePasswordChange());
        domain.setRoleId(roleId);
        domain.setStatus(entity.getStatus());
        
        return domain;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(this::mapToDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public List<User> findAll() {
        return  userJpaRepository.findAll().stream().map(this::mapToDomain).toList();
    }

    @Override
    public User update(User user) {
        RoleEntity roleEntity = roleJpaRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No existe el role"));
        UserEntity userEntity = userJpaRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("No existe el usuario"));

        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(roleEntity);
        userEntity.setRefreshTokenHash(user.getRefreshTokenHash());
        userEntity.setRequirePasswordChange(user.getRequirePasswordChange());
        userEntity.setStatus(user.getStatus());

        UserEntity updated = userJpaRepository.save(userEntity);

        return mapToDomain(updated);
    }

}
