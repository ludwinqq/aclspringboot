package bo.academia.carvic.presentation.permission.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReponsePermissionDto {

    private UUID id;
    private String name;
    private String title;
    private String description;
    private String module;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ReponsePermissionDto(UUID id, String name, String title, String description, String module, Integer status,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.module = module;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getModule() {
        return module;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
