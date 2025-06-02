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

    /**
     * Retorna uma página de usuários com base nas informações de paginação fornecidas (como número da página e tamanho).
     * Utiliza uma query JPQL para buscar todos os registros da entidade User.
     *
     * @param pageable objeto Pageable com informações de paginação
     * @return uma página (Page<User>) contendo usuários e metadados da paginação
     */
    @Query("select c from User c")
    Page<User> findAllPageable(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
