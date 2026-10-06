package bo.academia.carvic.infrastructure.persistence.role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;


@Repository 
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository repository;
    private final RoleMapper roleMapper;

    public RoleRepositoryImpl(RoleJpaRepository repository, @Qualifier("infraRoleMapper") RoleMapper roleMapper) {
        this.repository = repository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional // Transacción de escritura obligatoria por la tabla intermedia
    public Role save(Role role) {
        // Al ser una creación pura, convertimos el dominio completo a entidad
        RoleEntity entityToSave = roleMapper.toEntity(role);
        
        // Hibernate guarda en la BD, genera el UUID y las fechas de auditoría
        RoleEntity saved = repository.save(entityToSave);
        
        return roleMapper.toDomain(saved);
    }

    @Override
    @Transactional // Transacción de escritura obligatoria
    public Role update(Role role) {
        // 1. Buscamos la entidad existente para que Hibernate gestione su ciclo de vida
        RoleEntity entity = repository.findById(role.getId())
            .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));

        // 2. Modificamos los campos básicos del rol
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());
        //entity.setStatus(role.getStatus());

        // 3. Convertimos el nuevo estado del dominio a entidad para extraer sus permisos actualizados
        RoleEntity updatedFields = roleMapper.toEntity(role);
        
        // 4. Sincronizamos la lista de la tabla intermedia de manera segura usando tu método helper
        entity.updatePermissions(updatedFields.getPermissions());

        // 5. Al guardar, JPA se encarga de hacer los INSERTS y DELETES necesarios en la tabla intermedia
        RoleEntity updated = repository.save(entity);
        
        return roleMapper.toDomain(updated);
    }

    @Override
    @Transactional(readOnly = true) // Optimiza la velocidad de lectura en la base de datos
    public Optional<Role> findById(UUID id) {
        return repository.findById(id).map(roleMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return repository.findAll().stream()
                .map(roleMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        return repository.findByName(name).map(roleMapper::toDomain);
    }

}
