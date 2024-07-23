package br.com.davi.PPWebBack;

import br.com.davi.PPWebBack.usuario.controller.UserController;
import br.com.davi.PPWebBack.usuario.entity.User;
import br.com.davi.PPWebBack.usuario.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpWebBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpWebBackApplication.class, args);
	}

}
