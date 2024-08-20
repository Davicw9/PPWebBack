package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserIService {
    User save(User user);

    String delete(Long id);

    User update(Long id, User user);

    Page<User> findAll(int page, int size);
}
