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
        UserEntity entity = mapToEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return mapToDomain(saved);
    }

    private UserEntity mapToEntity(User user) {
        if ( user == null ) return null;
        RoleEntity roleEntity = null;
        if ( user.getRoleId() != null ) {
            roleEntity = roleJpaRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No existe rol"));
        }
        UserEntity entity = new UserEntity(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getRefreshTokenHash(),
            user.getRequirePasswordChange(),
            roleEntity
        );

        // Campos heredados
        if ( user.getStatus() != null ) {
            entity.setStatus(user.getStatus());
        }
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
        
        if ( entity == null ) return null;

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
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
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
        UserEntity entity = mapToEntity(user);
        UserEntity updated = userJpaRepository.save(entity);
        return mapToDomain(updated);
    }

}