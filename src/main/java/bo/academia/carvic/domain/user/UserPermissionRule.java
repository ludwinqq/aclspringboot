package bo.academia.carvic.domain.user;

import bo.academia.carvic.domain.permission.Permission;

public class UserPermissionRule {

    private final Permission permission;
    //private final Boolean permitted;
    private final AclStrategy strategy;
    
    public UserPermissionRule(Permission permission, AclStrategy strategy) {
        if ( permission == null ) {
            throw new IllegalArgumentException("El permiso no puede ser nulo");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("La estrategia del ACL es requerida");
        }
        this.permission = permission;
        this.strategy = strategy;
    }

    public Permission getPermission() { return permission; }
    public AclStrategy getStrategy() { return strategy; }

}
