package br.com.davi.PPWebBack.usuario.entity;

import br.com.davi.PPWebBack.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends PersistenceEntity implements UserDetails, Serializable {

    @Column(name = "nome", nullable = false)
    @Size(min = 2, max = 40)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "E-mail inválido")
    @NotBlank(message = "O email é obrigatorio")
    private String email;

    @Column(name = "nome_usuario", nullable = false, unique = true)
    @Size(min = 2)
    @NotBlank(message = "O login é obrigatorio")
    private String login;

    @Column(name = "senha", nullable = false)
    @Size(min = 4)
    @NotBlank(message = "O password é obrigatorio")
    private String password;

    // Implementações do UserDetails:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Sem roles, por enquanto.
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
