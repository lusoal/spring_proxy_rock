package br.usjt.rock.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.rock.model.bean.Administrator;
import br.usjt.rock.model.service.AdministratorService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	AdministratorService adminService;
	
	@PostMapping("/login/")
	public ResponseEntity<?> realizarLogin(@RequestBody Administrator admin) {
		boolean validate = adminService.findUserByLoginAndSenha(admin);
		if (validate) {
			return ResponseEntity.status(HttpStatus.OK).body(validate);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validate);
		}
	}
	
	@PostMapping("/cadastrar/")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody Administrator admin) {
		if(adminService.addUser(admin)) {
			return ResponseEntity.status(HttpStatus.OK).body(admin);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("False");
		}
		
	}
}
