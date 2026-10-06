package bo.academia.carvic.infrastructure.persistence.role;

import java.util.UUID;

import bo.academia.carvic.infrastructure.persistence.permission.PermissionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permissions") // Tabla intermedia
public class RolePermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Column(name = "permitted", nullable = false)
    private Boolean permitted;

    public RolePermissionEntity() {}

    public RolePermissionEntity(RoleEntity role, PermissionEntity permission, Boolean permitted) {
        this.role = role;
        this.permission = permission;
        this.permitted = permitted;
    }

    // Getters y Setters...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public RoleEntity getRole() { return role; }
    public void setRole(RoleEntity role) { this.role = role; }
    public PermissionEntity getPermission() { return permission; }
    public void setPermission(PermissionEntity permission) { this.permission = permission; }
    public Boolean getPermitted() { return permitted; }
    public void setPermitted(Boolean permitted) { this.permitted = permitted; }
}
