package br.com.davi.PPWebBack.usuario.controller;

import br.com.davi.PPWebBack.infrastructure.mapper.ObjectMapperUtil;
import br.com.davi.PPWebBack.usuario.dto.UserGetResponseDto;
import br.com.davi.PPWebBack.usuario.dto.UserPostRequestDto;
import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.findAll(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(userPage.getContent(), UserGetResponseDto.class));
    }

    /*@GetMapping(path = "/findall", produces = "application/json")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.userService.findAll(),
                        UserGetResponseDto.class));
    }*/

    //--------------------------------------------------------------------

    /*@GetMapping(path = "/findallTeste", produces = "application/json")
    public ResponseEntity<?> findAllTeste(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.findAll());
    }*/

    @PostMapping(path = "/save", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody UserPostRequestDto userPostRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapperUtil.map(userService.save(
                        objectMapperUtil.map(userPostRequestDto, User.class)
                ), UserGetResponseDto.class));
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(id));
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user){
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
