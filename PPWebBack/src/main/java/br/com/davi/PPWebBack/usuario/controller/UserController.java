package br.com.davi.PPWebBack.usuario.controller;

import br.com.davi.PPWebBack.infrastructure.mapper.ObjectMapperUtil;
import br.com.davi.PPWebBack.usuario.dto.UserGetResponseDto;
import br.com.davi.PPWebBack.usuario.dto.UserPostRequestDto;
import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class UserController {
    private final UserService userService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findall", produces = "application/json")
    public ResponseEntity<Page<UserGetResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.findAll(page, size);
        Page<UserGetResponseDto> userGetResponseDto = userPage.map(user -> objectMapperUtil.map(user, UserGetResponseDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(userGetResponseDto);
    }

    @PostMapping(path = "/save", consumes = "application/json")
    // Esse método é um endpoint que salva um novo usuário na base de dados.
    // Ele espera um objeto UserPostRequestDto vindo no corpo da requisição (JSON) e retorna um UserGetResponseDto.
    public ResponseEntity<UserGetResponseDto> save(@RequestBody @Valid UserPostRequestDto userPostRequestDto) {

        // Converte o DTO de entrada (UserPostRequestDto) em uma entidade User usando uma ferramenta de mapeamento (como ModelMapper).
        User user = objectMapperUtil.map(userPostRequestDto, User.class);

        // Salva a entidade User no banco de dados através do service.
        User savedUser = userService.save(user);

        // Converte o usuário salvo (entidade) em um DTO de resposta (UserGetResponseDto), que será retornado ao cliente.
        UserGetResponseDto responseDto = objectMapperUtil.map(savedUser, UserGetResponseDto.class);

        // Retorna a resposta HTTP com o status 201 (CREATED) e o corpo contendo o DTO de resposta.
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/update/name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestParam @NotBlank @Size(min = 2) String name) {
        userService.updateName(id, name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/email/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestParam @NotBlank @Email String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/login/{id}")
    public ResponseEntity<Void> updateLogin(@PathVariable Long id, @RequestParam @NotBlank @Size(min = 2) String login) {
        userService.updateLogin(id, login);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/password/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestParam @NotBlank @Size(min = 4) String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.noContent().build();
    }
}
