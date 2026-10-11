package bo.academia.carvic.presentation.user.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;

@Component("presentationUserMapper")
public class UserMapper {

    public UserResponseDto toResponse(User user) {
        if (user == null) return null;

        // CORREGIDO: Se cambió el tipo de List<UserResponseDto> a List<UserResponseDto.CustomRuleDto>
        List<UserResponseDto.CustomRuleDto> customRules = user.getPermissionRules().stream()
                .map(r -> new UserResponseDto.CustomRuleDto(
                        r.getPermission().getId(), 
                        r.getPermission().getName(), 
                        r.getStrategy() // Mapea directamente el AclStrategy (Enum)
                ))
                .toList();

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoleId(user.getRoleId());
        userDto.setStatus(user.getStatus());
        userDto.setCustomPermissions(customRules);
        return userDto;
    }
}
