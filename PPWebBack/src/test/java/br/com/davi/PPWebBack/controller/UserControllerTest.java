package br.com.davi.PPWebBack.usuario.controller;

import br.com.davi.PPWebBack.infrastructure.mapper.ObjectMapperUtil;
import br.com.davi.PPWebBack.usuario.dto.UserGetResponseDto;
import br.com.davi.PPWebBack.usuario.dto.UserPostRequestDto;
import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ObjectMapperUtil objectMapperUtil;

    @Test
    public void testGetAll() throws Exception {
        // Mock do serviço
        Page<User> userPage = new PageImpl<>(Collections.singletonList(new User()));
        Mockito.when(userService.findAll(0, 10)).thenReturn(userPage);

        // Mock do ObjectMapperUtil
        UserGetResponseDto responseDto = new UserGetResponseDto();
        Mockito.when(objectMapperUtil.map(Mockito.any(User.class), Mockito.eq(UserGetResponseDto.class)))
                .thenReturn(responseDto);

        // Executa a requisição e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/findall")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    public void testSave() throws Exception {
        // Mock do serviço e ObjectMapperUtil
        UserPostRequestDto requestDto = new UserPostRequestDto();
        User user = new User();
        User savedUser = new User();
        UserGetResponseDto responseDto = new UserGetResponseDto();

        Mockito.when(objectMapperUtil.map(requestDto, User.class)).thenReturn(user);
        Mockito.when(userService.save(user)).thenReturn(savedUser);
        Mockito.when(objectMapperUtil.map(savedUser, UserGetResponseDto.class)).thenReturn(responseDto);

        // Executa a requisição e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Davi\"}")) // Exemplo de corpo JSON
                .andExpect(status().isCreated());
    }

    @Test
    public void testDelete() throws Exception {
        // Executa a requisição e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdate() throws Exception {
        // Executa a requisição e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Davi Atualizado\"}")) // Exemplo de corpo JSON
                .andExpect(status().isNoContent());
    }
}
