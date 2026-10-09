package bo.academia.carvic.presentation.user.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;

@Component("presentationUserMapper")
public class UserMapper {

    public UserResponseDto toResponse(User user) {
        if (user == null) return null;

        List<UserResponseDto.CustomRuleDto> customRules = user.getPermissionRules().stream()
                .map(r -> new UserResponseDto.CustomRuleDto(
                        r.getPermission().getId(), 
                        r.getPermission().getName(), 
                        r.isPermitted()
                ))
                .toList();

        return new UserResponseDto(
                user.getId(), 
                user.getUsername(), 
                user.getEmail(), 
                user.getRoleId(), 
                user.getStatus(), 
                customRules
        );
    }
}
