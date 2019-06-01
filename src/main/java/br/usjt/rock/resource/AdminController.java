package br.usjt.rock.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.usjt.rock.model.bean.Administrator;
import br.usjt.rock.model.bean.Users;
import br.usjt.rock.model.service.AdministratorService;
import br.usjt.rock.model.service.UserService;

@RestController
@RequestMapping("/api/login")
public class AdminController {

	@Autowired
	AdministratorService adminService;
	@Autowired
	UserService userService;

	ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<?> realizarLogin(@RequestBody String user, @RequestParam("userType") String userType, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		boolean validate = false;
		System.out.println(userType);
		if ("admin".equals(userType)) {
			Administrator admin = mapper.readValue(user, Administrator.class);
			validate = adminService.findUserByLoginAndSenha(admin);
		} else if ("torcedor".equals(userType)) {
			Users torcedor = mapper.readValue(user, Users.class);
			validate = userService.findUserByLoginAndSenha(torcedor);
		}

		ObjectNode jsonObject = mapper.createObjectNode();

		if (validate) {
			request.getSession().setAttribute("logged", true);
			jsonObject.put("message", "True");
			jsonObject.put("type", userType);
			return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
		} else {
			jsonObject.put("message", "False");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject);
		}
	}

}
