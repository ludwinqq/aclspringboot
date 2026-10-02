package bo.academia.carvic.infrastructure.persistence.role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;

@Repository 
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository repository;

    public RoleRepositoryImpl(RoleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = new RoleEntity();

        if ( role.getId() != null ) {
            entity.setId(role.getId());
        }

        entity.setName(role.getName());
        entity.setDescription(role.getDescription());
        
        if ( role.getStatus() != null ) {
            entity.setStatus(role.getStatus()); // Mantiene el status = 1 por defecto de tu dominio
        }
        

        // Hibernate guarda en la BD, genera el UUID, createdAt y updatedAt automáticamente
        RoleEntity saved = repository.save(entity);
        
        // Convertimos la entidad guardada con todos los datos reales del servidor de regreso al Dominio
        return mapToDomain(saved);
    }

    @Override
    public Role update(Role role) {
        // Buscamos la entidad existente para no crear un duplicado
        RoleEntity entity = repository.findById(role.getId())
            .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));

        // Modificamos solo los campos necesarios
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());
        entity.setStatus(role.getStatus());

        // Al guardar, @UpdateTimestamp de Hibernate se encarga de actualizar 'updated_at' en el servidor
        RoleEntity updated = repository.save(entity);
        
        return mapToDomain(updated);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll().stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name).map(this::mapToDomain);
    }

    /**
     * Método auxiliar privado para mapear TODOS los campos desde la Capa de Infraestructura (Base de datos)
     * hacia la Capa de Dominio, asegurando que las fechas y estados nunca se queden en null.
     */
    private Role mapToDomain(RoleEntity entity) {
        Role domain = new Role();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setStatus(entity.getStatus());
        return domain;
    }
}
