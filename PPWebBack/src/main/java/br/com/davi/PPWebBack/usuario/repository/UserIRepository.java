package br.com.davi.PPWebBack.usuario.repository;

import br.com.davi.PPWebBack.usuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIRepository extends JpaRepository<User, Long> {
}
