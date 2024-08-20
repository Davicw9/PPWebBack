package br.com.davi.PPWebBack.usuario.repository;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIRepository extends JpaRepository<User, Long> {

    @Query("select c from User c")
    Page<User> findAllPageable(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
