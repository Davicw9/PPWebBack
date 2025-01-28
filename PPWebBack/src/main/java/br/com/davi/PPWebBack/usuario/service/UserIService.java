package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserIService {
    User save(User user);

    void delete(Long id);


    void updateName(Long id, String name);


    void updateEmail(Long id, String email);


    void updateLogin(Long id, String login);


    void updatePassword(Long id, String password);

    Page<User> findAll(int page, int size);
}
