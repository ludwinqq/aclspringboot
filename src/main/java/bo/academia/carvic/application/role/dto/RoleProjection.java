package bo.academia.carvic.application.role.dto;

import java.util.UUID;

public interface RoleProjection {

    UUID getId();
    String getName();
    String getDescription();
    Integer getStatus();
}
