package bo.academia.carvic.presentation.user;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.academia.carvic.application.user.CreateUserUseCase;
import bo.academia.carvic.application.user.FindAllUserUseCase;
import bo.academia.carvic.application.user.FindByEmailUserUseCase;
import bo.academia.carvic.application.user.FindByIdUserUseCase;
import bo.academia.carvic.application.user.FindByUsernameUserUseCase;
import bo.academia.carvic.application.user.UpdateUserUseCase;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.user.dto.CreateUserRequestDto;
import bo.academia.carvic.presentation.user.dto.UpdateUserRequestDto;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;
import bo.academia.carvic.presentation.user.mapper.UserMapper;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final FindByEmailUserUseCase findByEmailUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final UserMapper userMapper;
    
    public UserController(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
            FindAllUserUseCase findAllUserUseCase, FindByEmailUserUseCase findByEmailUserUseCase,
            FindByIdUserUseCase findByIdUserUseCase, FindByUsernameUserUseCase findByUsernameUserUseCase, UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.findByEmailUserUseCase = findByEmailUserUseCase;
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.findByUsernameUserUseCase = findByUsernameUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping 
    public UserResponseDto create(@Valid @RequestBody CreateUserRequestDto request) {
        // 1. Convertimos DTO a Dominio usando el mapper
        User user = userMapper.toDomain(request);
        // 2. Ejecutamos el Caso de Uso (Lógica de negocio pura)
        User createdUser = createUserUseCase.execute(user);
        // 3. Convertimos Dominio a Response DTO
        return userMapper.toResponse(createdUser);
    }

    @GetMapping 
    public List<UserResponseDto> getAll() {
        return findAllUserUseCase.execute().stream().map(userMapper::toResponse).toList();
    }

    @GetMapping ("/{id}")
    public UserResponseDto getById(@PathVariable UUID id) {
        //User user = findByIdUserUseCase.execute(id);
        return userMapper.toResponse(findByIdUserUseCase.execute(id));
    }

    @GetMapping ("/email/{email}")
    public UserResponseDto getByEmail(@PathVariable String email) {
        return userMapper.toResponse(findByEmailUserUseCase.execute(email));
    }

    @PutMapping ("/{id}")
    public UserResponseDto modify(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequestDto requestDto) {
        User user = userMapper.toDomain(requestDto);
        User updateUser = updateUserUseCase.execute(id, user);
        return userMapper.toResponse(updateUser);
    }

    @GetMapping ("/username/{username}")
    public UserResponseDto getByUsername(@PathVariable String username) {
        return userMapper.toResponse(findByUsernameUserUseCase.execute(username));
    }
    
}