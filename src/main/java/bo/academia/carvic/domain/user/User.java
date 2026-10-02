package bo.academia.carvic.domain.user;

import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private String refreshTokenHash;
    private Boolean requirePasswordChange;
    private UUID roleId;
    private Integer status = 1;
    
    public User() {
    }

    public User(UUID id, String username, String email, String password, String refreshTokenHash,
            Boolean requirePasswordChange, UUID roleId, Integer status) {
        
        if ( username == null || username.isBlank() ) {
            throw new IllegalArgumentException("Username requerido");
        }

        if ( password == null || password.isBlank() ) {
            throw new IllegalArgumentException("Password requerido");
        }

        if ( email == null || email.isBlank() ) {
            throw new IllegalArgumentException("email requerido");
        }

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.refreshTokenHash = refreshTokenHash;
        this.requirePasswordChange = requirePasswordChange;
        this.roleId = roleId;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
  
}
