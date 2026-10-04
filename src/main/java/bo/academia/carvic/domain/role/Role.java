package bo.academia.carvic.domain.role;

import java.time.LocalDateTime;
import java.util.UUID;

public class Role {

    private UUID id;
    private String name;
    private String description;
    private Integer status = 1;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Role() {
    }

    public Role(UUID id, String name, String description, Integer status, LocalDateTime createdAt, LocalDateTime updatedAt) {

        if ( name == null || name.isBlank() ) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateDetails(String name, String description) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void getCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void getUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
