package br.com.davi.PPWebBack.usuario.entity;

import br.com.davi.PPWebBack.infrastructureEntity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends PersistenceEntity implements Serializable{
    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nome_usuario", nullable = false, unique = true)
    private String login;

    @Column(name = "senha", nullable = false)
    private String password;
}
