package bo.academia.carvic.presentation.user;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import bo.academia.carvic.application.user.CreateUserUseCase;
import bo.academia.carvic.application.user.FindAllUserUseCase;
import bo.academia.carvic.application.user.FindByIdUserUseCase;
import bo.academia.carvic.application.user.UpdateUserUseCase;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.user.dto.UserRequest;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;
import bo.academia.carvic.presentation.user.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final UserMapper userMapper;

    public UserController(
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            FindByIdUserUseCase findByIdUserUseCase,
            FindAllUserUseCase findAllUserUseCase,
            @Qualifier("presentationUserMapper") UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResponseDto create(@RequestBody UserRequest request) {
        User user = createUserUseCase.execute(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getPermissions(),
                request.getRoleId()
        );
        return userMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable UUID id, @RequestBody UserRequest request) {
        User updatedUser = updateUserUseCase.execute(
                id,
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRoleId(),
                request.getPermissions()
        );
        return userMapper.toResponse(updatedUser);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable UUID id) {
        return userMapper.toResponse(findByIdUserUseCase.execute(id));
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return findAllUserUseCase.execute().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
