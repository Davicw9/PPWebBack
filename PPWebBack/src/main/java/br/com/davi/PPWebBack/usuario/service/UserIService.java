package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserIService {
    User save(User user);

    String delete(Long id);

    User update(Long id, User user);

    List<User> findAll();
}
