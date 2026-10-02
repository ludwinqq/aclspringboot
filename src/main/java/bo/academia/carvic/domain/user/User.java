package bo.academia.carvic.domain.user;

import java.util.UUID;

import bo.academia.carvic.domain.BaseAuditableEntity;

public class User extends BaseAuditableEntity {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private String refreshTokenHash;
    private Boolean requirePasswordChange;
    private UUID roleId;
    
    public User() {
    }

    public User(UUID id, String username, String email, String password, String refreshTokenHash,
            Boolean requirePasswordChange, UUID roleId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.refreshTokenHash = refreshTokenHash;
        this.requirePasswordChange = requirePasswordChange;
        this.roleId = roleId;
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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
    
}
