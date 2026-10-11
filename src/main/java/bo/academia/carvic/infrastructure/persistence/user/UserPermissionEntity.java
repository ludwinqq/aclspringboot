package bo.academia.carvic.infrastructure.persistence.user;

import java.util.UUID;

import bo.academia.carvic.domain.user.AclStrategy;
import bo.academia.carvic.infrastructure.persistence.permission.PermissionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "user_permissions")
public class UserPermissionEntity {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "permission_id", nullable = false)
    private PermissionEntity permission;

    //@Column (name = "permitted", nullable = false)
    //private Boolean permitted;
    @Enumerated (EnumType.STRING)
    @Column(name = "strategy", nullable = false, length = 30)
    private AclStrategy strategy;

    public UserPermissionEntity() {
    }

    public UserPermissionEntity(UserEntity user, PermissionEntity permission, AclStrategy strategy) {
        this.user = user;
        this.permission = permission;
        this.strategy = strategy;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PermissionEntity getPermission() {
        return permission;
    }

    public void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }

    public AclStrategy getStrategy() { return strategy; }
    public void setStrategy(AclStrategy strategy) { this.strategy = strategy; }
    
}
