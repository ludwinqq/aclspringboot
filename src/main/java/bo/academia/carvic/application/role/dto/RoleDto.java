package bo.academia.carvic.application.role.dto;

import java.util.UUID;

public class RoleDto {
    private UUID id;
    private String name;
    private String description;
    public RoleDto(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    
}
