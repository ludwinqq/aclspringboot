package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleJpaRepository;

public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public User save(User user) {
        RoleEntity roleEntity = roleJpaRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("No existe el rol"));
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRefreshTokenHash(user.getRefreshTokenHash());
        userEntity.setRequirePasswordChange(user.getRequirePasswordChange());
        userEntity.setRole(roleEntity);
        userEntity.setStatus(user.getStatus());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());

        UserEntity saved = userJpaRepository.save(userEntity);

        User usuario = new User();
        usuario.setId(saved.getId());
        usuario.setUsername(saved.getUsername());
        usuario.setEmail(saved.getEmail());
        usuario.setPassword(saved.getPassword());
        usuario.setRefreshTokenHash(saved.getRefreshTokenHash());
        usuario.setRequirePasswordChange(saved.getRequirePasswordChange());
        usuario.setStatus(saved.getStatus());
        usuario.setCreatedAt(saved.getCreatedAt());
        usuario.setUpdatedAt(saved.getUpdatedAt());

        return usuario;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setRefreshTokenHash(entity.getRefreshTokenHash());
        user.setRequirePasswordChange(entity.getRequirePasswordChange());
        user.setRoleId(entity.getRole().getId());
        user.setStatus(entity.getStatus());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        return user;
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
        return  userJpaRepository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        return save(user);
    }

}
