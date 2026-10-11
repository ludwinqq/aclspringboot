package bo.academia.carvic.application.user.dto;

import java.util.UUID;

public class UserListSummaryResponse {
    private final UUID id;
    private final String username;
    private final String email;
    private final String roleName; // <-- Campo unido mediante el JOIN
    private final Integer status;

    public UserListSummaryResponse(UUID id, String username, String email, String roleName, Integer status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleName = roleName;
        this.status = status;
    }

    // Getters...
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRoleName() { return roleName; }
    public Integer getStatus() { return status; }
}
