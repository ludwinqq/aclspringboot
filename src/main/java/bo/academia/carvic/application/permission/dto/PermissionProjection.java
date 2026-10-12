package bo.academia.carvic.application.permission.dto;

import java.util.UUID;

public interface PermissionProjection {
    UUID getId();
    String getName();
    String getTitle();
    String getModule();
}
