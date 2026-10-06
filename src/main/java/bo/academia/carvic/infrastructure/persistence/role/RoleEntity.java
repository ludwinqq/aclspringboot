package bo.academia.carvic.infrastructure.persistence.role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import bo.academia.carvic.infrastructure.persistence.BaseAuditableData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "roles")
public class RoleEntity extends BaseAuditableData{

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (
        name = "name",
        nullable = false,
        length = 100
    )
    private String name;

    @Column (
        name = "description",
        length = 255
    )
    private String description;

    // Relación mapeada hacia la tabla intermedia con control total de cascada
    @OneToMany (mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePermissionEntity> permissions = new ArrayList<>();

    public RoleEntity() {
    }

    public RoleEntity(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Helper metod para actualizar de golpe las reglas de permisos en JPA de manera segura
    public void updatePermissions(List<RolePermissionEntity> newPermissions) {
        this.permissions.clear();
        if ( newPermissions != null ) {
            newPermissions.forEach(p -> p.setRole(this));
            this.permissions.addAll(newPermissions);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RolePermissionEntity> getPermissions() { return permissions; }
    public void setPermissions(List<RolePermissionEntity> permissions) { this.permissions = permissions; }
}