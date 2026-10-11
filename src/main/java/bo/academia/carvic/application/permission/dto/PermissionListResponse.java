package bo.academia.carvic.application.permission.dto;

import java.util.UUID;

public class PermissionListResponse {

    private final UUID id;
    private final String name;
    private final String title;
    private final String module;
    
    public PermissionListResponse(UUID id, String name, String title, String module) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.module = module;
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

    public String getModule() {
        return module;
    }

    
}
