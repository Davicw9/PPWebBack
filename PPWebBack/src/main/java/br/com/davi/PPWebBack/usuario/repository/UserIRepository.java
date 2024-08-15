package br.com.davi.PPWebBack.usuario.repository;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLogin(String login);
}
