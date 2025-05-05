package br.com.davi.PPWebBack.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostRequestDto {

    @JsonProperty(value = "name")
    @Size(min = 2, max = 40)
    private String name;

    @JsonProperty(value = "email")
    @Email(message = "E-mail inválido")
    @NotBlank(message = "O email é obrigatorio")
    private String email;

    @JsonProperty(value = "login")
    @Size(min = 2)
    @NotBlank(message = "O login é obrigatorio")
    private String login;

    @JsonProperty(value = "password")
    @NotBlank(message = "O password é obrigatorio")
    @Size(min = 4)
    private String password;
}
