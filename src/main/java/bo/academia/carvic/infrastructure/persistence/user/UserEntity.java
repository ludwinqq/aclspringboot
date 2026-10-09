package bo.academia.carvic.infrastructure.persistence.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bo.academia.carvic.infrastructure.persistence.BaseAuditableData;
import bo.academia.carvic.infrastructure.persistence.permission.PermissionEntity;
import bo.academia.carvic.infrastructure.persistence.role.RoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "users")
public class UserEntity extends BaseAuditableData{

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (
        name = "username",
        unique = true,
        length = 100,
        nullable = false
    )
    private String username;

    @Column (
        name = "email",
        unique = true,
        length = 100,
        nullable = false
    )
    private String email;

    @Column (
        name = "password",
        nullable = false
    )
    private String password;

    @Column (
        name = "refresh_token_hash"
    )
    private String refreshTokenHash;
    
    @Column (
        name = "require_password_change"
    )
    private Boolean requirePasswordChange = true;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "role_id", nullable = false)
    private RoleEntity role;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPermissionEntity> permissions = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(UUID id, String username, String email, String password, String refreshTokenHash,
            Boolean requirePasswordChange, RoleEntity role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.refreshTokenHash = refreshTokenHash;
        this.requirePasswordChange = requirePasswordChange;
        this.role = role;
    }

    public void syncPermissions(List<UserPermissionEntity> newPermissions) {
        this.permissions.clear();
        if ( newPermissions != null ) {
            newPermissions.forEach(p -> p.setUser(this));
            this.permissions.addAll(newPermissions);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshTokenHash() {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(String refreshTokenHash) {
        this.refreshTokenHash = refreshTokenHash;
    }

    public Boolean getRequirePasswordChange() {
        return requirePasswordChange;
    }

    public void setRequirePasswordChange(Boolean requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
    
    public List<UserPermissionEntity> getPermissions() { return permissions; }
    public void setPermissions(List<UserPermissionEntity> permissions) { this.permissions = permissions; }

}
