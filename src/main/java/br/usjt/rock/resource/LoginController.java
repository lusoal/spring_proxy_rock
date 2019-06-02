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
import br.usjt.rock.model.bean.Torcedor;
import br.usjt.rock.model.service.AdministratorService;
import br.usjt.rock.model.service.TorcedorService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private AdministratorService adminService;
	@Autowired
	private TorcedorService torcedorService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<?> realizarLogin(@RequestBody String body, @RequestParam("userType") String userType, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {

		Object obj = null;
		System.out.println(userType);

		if ("admin".equals(userType)) {
			obj = adminService.findUserByLoginAndSenha(mapper.readValue(body, Administrator.class));
		} else if ("torcedor".equals(userType)) {
			obj = torcedorService.findUserByLoginAndSenha(mapper.readValue(body, Torcedor.class));
		}

		if (obj != null) {
			request.getSession().setAttribute("logged", true);
			
			if (obj instanceof Torcedor) {
				return ResponseEntity.status(HttpStatus.OK).body((Torcedor) obj);
			} else if (obj instanceof Administrator) {
				return ResponseEntity.status(HttpStatus.OK).body((Administrator) obj);
			}
		}

		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("message", "False");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject);
	}
}