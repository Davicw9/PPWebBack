package br.com.davi.PPWebBack.usuario.controller;

import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/findall", produces = "application/json")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @PostMapping(path = "/save", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(id));
    }

    @PutMapping(path = "/update", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
