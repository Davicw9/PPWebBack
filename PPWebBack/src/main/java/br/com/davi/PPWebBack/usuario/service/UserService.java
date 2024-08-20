package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.exception.EntityNotFoundException;
import br.com.davi.PPWebBack.usuario.exception.InvalidRegistrationInformationException;
import br.com.davi.PPWebBack.usuario.exception.UserAlreadyExistsException;
import br.com.davi.PPWebBack.usuario.repository.UserIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserIService {

    @Autowired
    UserIRepository userIRepository;

    @Override
    @Transactional
    public User save(User user) {
        Optional <User> userOptionalLogin = userIRepository.findByLogin(user.getLogin());
        Optional <User> userOptionalEmail = userIRepository.findByEmail(user.getEmail());
        if (userOptionalLogin.isPresent() || userOptionalEmail.isPresent()) {
            throw new UserAlreadyExistsException("Nome de Usuario ou email ja cadastrado");
        }
        return this.userIRepository.save(user);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        userIRepository.deleteById(id);
        return "DELETED";
    }

    @Override
    @Transactional
    public User update(Long id, User user) {

        User userExists = this.userIRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id=%s não existe", id))
        );

        if (user.getLogin().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getName().isBlank()) {
            throw new InvalidRegistrationInformationException("falha na atualizacao, todos os campos devem ser preenchidos corretamente");
        }

        user.setId(id);

        return  this.userIRepository.save(user);
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userIRepository.findAllPageable(pageable);
    }
}
