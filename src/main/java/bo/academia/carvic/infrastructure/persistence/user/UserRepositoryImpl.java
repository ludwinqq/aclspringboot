package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleJpaRepository;

@Repository 
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserMapper userMapper;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository, UserMapper userMapper, UserMapper userMapper_1) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.userMapper = userMapper;

    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return  userJpaRepository.findAll().stream().map(userMapper::toDomain).toList();
    }

    @Override
    public User update(User user) {
        UserEntity existing = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + user.getId()));
        
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setStatus(user.getStatus());
        existing.setRequirePasswordChange(user.getRequirePasswordChange());
        existing.setRefreshTokenHash(user.getRefreshTokenHash());

        RoleEntity roleRef = new RoleEntity();
        roleRef.setId(user.getRoleId());
        existing.setRole(roleRef);

        UserEntity state = userMapper.toEntity(user);
        existing.syncPermissions(state.getPermissions());

        UserEntity saved = userJpaRepository.save(existing);
        return userMapper.toDomain(saved);
    }

}