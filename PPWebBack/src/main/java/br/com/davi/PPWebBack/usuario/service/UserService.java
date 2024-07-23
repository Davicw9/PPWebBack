package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.repository.UserIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserIService {

    @Autowired
    UserIRepository userIRepository;

    @Override
    public User save(User user) {
        return userIRepository.save(user);
    }

    @Override
    public String delete(Long id) {
        userIRepository.deleteById(id);
        return "DELETED";
    }

    @Override
    public User update(User user) {
        return  userIRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userIRepository.findAll();
    }
}
