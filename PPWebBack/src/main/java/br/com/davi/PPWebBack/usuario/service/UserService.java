package br.com.davi.PPWebBack.usuario.service;

import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.exception.EntityNotFoundException;
import br.com.davi.PPWebBack.usuario.exception.InvalidRegistrationInformationException;
import br.com.davi.PPWebBack.usuario.exception.UserAlreadyExistsException;
import br.com.davi.PPWebBack.usuario.repository.UserIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserIService {


    private final UserIRepository userIRepository;

    @Override
    @Transactional
    public User save(User user) {
        log.info("Salvando usuário com login: {}", user.getLogin());
        Optional <User> userOptionalLogin = userIRepository.findByLogin(user.getLogin());
        Optional <User> userOptionalEmail = userIRepository.findByEmail(user.getEmail());
        if (userOptionalLogin.isPresent()) {
            throw new UserAlreadyExistsException("Login já cadastrado");
        }
        if (userOptionalEmail.isPresent()) {
            throw new UserAlreadyExistsException("Email já cadastrado");
        }
        return this.userIRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando usuário com id: {}", id);

        if (!userIRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Usuário id=%s não existe", id));
        }
        userIRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateName(Long id, String name) {
        log.info("Atualizando nome do usuário com id: {}", id);

        User user = userIRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não existe", id))
        );

        if (name == null || name.isBlank()) {
            throw new InvalidRegistrationInformationException("O nome não pode ser vazio");
        }

        user.setName(name);
        userIRepository.save(user);
    }

    @Override
    @Transactional
    public void updateEmail(Long id, String email) {
        log.info("Atualizando email do usuário com id: {}", id);

        User user = userIRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não existe", id))
        );

        if (email == null || email.isBlank()) {
            throw new InvalidRegistrationInformationException("O email não pode ser vazio");
        }

        // Verifica se o novo email já existe (exceto para o próprio usuário)
        Optional<User> userWithSameEmail = userIRepository.findByEmail(email);
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new UserAlreadyExistsException("Email já cadastrado");
        }

        user.setEmail(email);
        userIRepository.save(user);
    }

    @Override
    @Transactional
    public void updateLogin(Long id, String login) {
        log.info("Atualizando login do usuário com id: {}", id);

        User user = userIRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não existe", id))
        );

        if (login == null || login.isBlank()) {
            throw new InvalidRegistrationInformationException("O login não pode ser vazio");
        }

        // Verifica se o novo login já existe (exceto para o próprio usuário)
        Optional<User> userWithSameLogin = userIRepository.findByLogin(login);
        if (userWithSameLogin.isPresent() && !userWithSameLogin.get().getId().equals(id)) {
            throw new UserAlreadyExistsException("Login já cadastrado");
        }

        user.setLogin(login);
        userIRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String password) {
        log.info("Atualizando senha do usuário com id: {}", id);

        User user = userIRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não existe", id))
        );

        if (password == null || password.isBlank()) {
            throw new InvalidRegistrationInformationException("A senha não pode ser vazia");
        }

        user.setPassword(password);
        userIRepository.save(user);
    }

    @Override
    public Page<User> findAll(int page, int size) {
        log.info("Buscando todos os usuários - página: {}, tamanho: {}", page, size);

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Página e tamanho devem ser valores positivos");
        }

        Pageable pageable = PageRequest.of(page, size);
        return userIRepository.findAllPageable(pageable);
    }
}
