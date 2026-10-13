package bo.academia.carvic.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.domain.user.UserRepository;
import bo.academia.carvic.infrastructure.common.libraries.PaginationHelper;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleJpaRepository;

@Repository 
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserMapper userMapper;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository, UserMapper userMapper) {
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

    @Override
    public PageResponse<User> findAllPaged(PageableRequest pageableRequest) {
        // 1. Traducir la peticion de Spring Pageable usando la libreria de paginacion
        Pageable sprinPageable = PaginationHelper.toSpringPageable(pageableRequest);
        // 2. Ejecutar la consulta a la base de datos (Spring Data JPA)
        Page<UserEntity> entityPage = userJpaRepository.findAll(sprinPageable);
        // 3. Usa la libreria para mapear el Page<UserEntity> a PageResponse<User> (entidad de dominio)
        return PaginationHelper.mapPaging(entityPage, entity -> 
            new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                null,
                null,
                null,
                entity.getRole().getId(),
                null
            )
        );
    }

}