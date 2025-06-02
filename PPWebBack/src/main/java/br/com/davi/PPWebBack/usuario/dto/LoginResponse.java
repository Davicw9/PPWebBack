package br.com.davi.PPWebBack.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de resposta com o token JWT.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
