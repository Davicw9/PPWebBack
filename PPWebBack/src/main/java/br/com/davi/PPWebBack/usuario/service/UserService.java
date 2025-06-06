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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserIService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserIRepository userIRepository;

    private User getUserOrThrow(Long id) {
        return userIRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário " + id + " não existe"));
    }


    @Override
    @Transactional
    public User save(User user) {
        log.info("Salvando usuário com login: {}", user.getLogin());
        Optional<User> userOptionalLogin = userIRepository.findByLogin(user.getLogin());
        Optional<User> userOptionalEmail = userIRepository.findByEmail(user.getEmail());

        if (userOptionalLogin.isPresent()) {
            throw new UserAlreadyExistsException("Login já cadastrado");
        }
        if (userOptionalEmail.isPresent()) {
            throw new UserAlreadyExistsException("Email já cadastrado");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // <--- Aqui criptografa
        return this.userIRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deletando usuário com id: {}", id);

        if (!userIRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário " + id + " não existe");
        }
        userIRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateName(Long id, String name) {
        log.info("Atualizando nome do usuário com id: {}", id);

        User user = getUserOrThrow(id);

        user.setName(name);
        userIRepository.save(user);
    }

    @Override
    @Transactional
    public void updateEmail(Long id, String email) {
        log.info("Atualizando email do usuário com id: {}", id);

        User user = getUserOrThrow(id);

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

        User user = getUserOrThrow(id);

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

        User user = getUserOrThrow(id);

        if (password == null || password.length() < 4) {
            throw new InvalidRegistrationInformationException("Senha deve ter no mínimo 4 caracteres");
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
