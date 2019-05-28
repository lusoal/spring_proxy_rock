package br.usjt.rock.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.usjt.rock.model.bean.Administrator;
import br.usjt.rock.model.service.AdministratorService;

@RestController
@RequestMapping("/api/login/")
public class AdminController {
	
	@Autowired
	AdministratorService adminService;
	
	@PostMapping()
	public ResponseEntity<?> realizarLogin(@RequestBody Administrator admin) {
		boolean validate = adminService.findUserByLoginAndSenha(admin);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		
		if (validate) {
			jsonObject.put("message", "True");
			return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
		}else {
			jsonObject.put("message", "False");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject);
		}
	}
	
}
