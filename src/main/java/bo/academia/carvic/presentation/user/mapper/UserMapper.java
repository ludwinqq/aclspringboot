package bo.academia.carvic.presentation.user.mapper;

import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.domain.role.RoleRepository;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.role.dto.RoleResponseDto;
import bo.academia.carvic.presentation.role.mapper.RoleMapper;
import bo.academia.carvic.presentation.user.dto.CreateUserRequestDto;
import bo.academia.carvic.presentation.user.dto.UpdateUserRequestDto;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;

@Component 
public class UserMapper {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public UserMapper(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public User toDomain(UpdateUserRequestDto request) {
        if (request == null ) return null;
        User user = new User();
        user.setRoleId(request.getRoleId());
        user.setPassword(request.getPassword());
        return user;
    }

    public User toDomain(CreateUserRequestDto request) {
        if (request == null) return null;
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRoleId(request.getRoleId());
        return user;
    }

    public UserResponseDto toResponse(User user) {
        if (user == null) return null;
        /*RoleResponseDto roleResponseDto = null;
        if (user.getRoleId() != null) {
            Role role = roleRepository.findById(user.getRoleId()).orElse(null);
            if (role != null) {
                roleResponseDto = roleMapper.toResponse(role); // Convertimos el rol a su DTO de respuesta
            }
        }*/
        
        return new UserResponseDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getStatus(),
            user.getRoleId(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
