package bo.academia.carvic.domain.role;

import bo.academia.carvic.domain.permission.Permission;

public class RolePermissionRule {

    private final Permission permission;
    private final Boolean permitted;
    
    public RolePermissionRule(Permission permission, Boolean permitted) {
        if ( permission == null ) {
            throw new IllegalArgumentException("El permiso no puede ser nulo");
        }
        this.permission = permission;
        this.permitted = permitted;
    }

    public Permission getPermission() { return permission; }
    public Boolean isPermitted() { return permitted; }
}
