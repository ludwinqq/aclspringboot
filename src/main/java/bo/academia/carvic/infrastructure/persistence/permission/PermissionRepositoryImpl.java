package bo.academia.carvic.infrastructure.persistence.permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.domain.permission.PermissionRepository;
import bo.academia.carvic.infrastructure.common.libraries.PaginationHelper;

@Repository 
public class PermissionRepositoryImpl implements PermissionRepository {

    private PermissionJpaRepository repository;
 
    public PermissionRepositoryImpl(PermissionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Permission save(Permission permission) {
        PermissionEntity entity = new PermissionEntity();
        entity.setName(permission.getName());
        entity.setTitle(permission.getTitle());
        entity.setDescription(permission.getDescription());
        entity.setModule(permission.getModule());
        PermissionEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Permission update(Permission permission) {
        PermissionEntity entity = repository.findById(permission.getId()).orElseThrow(() -> new RuntimeException("Role not found with ID: " + permission.getId()));
        entity.setName(permission.getName());
        entity.setTitle(permission.getTitle());
        entity.setDescription(permission.getDescription());
        entity.setModule(permission.getModule());
        PermissionEntity updated = repository.save(entity);

        return mapToDomain(updated);
    }

    @Override
    public List<Permission> findAll() {
        return repository.findAll().stream().map(this::mapToDomain).toList();
    }

    @Override
    public Optional<Permission> findById(UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return repository.findByName(name).map(this::mapToDomain);
    }

    private Permission mapToDomain(PermissionEntity entity) {
        Permission permission = new Permission();
        permission.setId(entity.getId());
        permission.setName(entity.getName());
        permission.setTitle(entity.getTitle());
        permission.setDescription(entity.getDescription());
        permission.setModule(entity.getModule());
        permission.setStatus(entity.getStatus());
        permission.setCreatedAt(entity.getCreatedAt());
        permission.setUpdatedAt(entity.getUpdatedAt());
        return permission;
    }

    @Override
    public PageResponse<Permission> findAllPaged(PageableRequest pageableRequest) {
        //1. Traduce la peticion a Spring Pageable usando la libreria de paginacion
        Pageable sprinPageable = PaginationHelper.toSpringPageable(pageableRequest);
        //2. Ejecuta la consulta a la base de datos (Spring Data JPA)
        Page<PermissionEntity> entityPage = repository.findAll(sprinPageable);
        //3. Usa la libreria para mapear el Page<PermissionEntity> a PageResponse<Permission> (entidad de dominio)
        return PaginationHelper.mapPaging(entityPage, entity -> 
            new Permission(
                entity.getId(),
                entity.getName(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getModule(),
                null,
                null,
                null
            ));
    }
}
