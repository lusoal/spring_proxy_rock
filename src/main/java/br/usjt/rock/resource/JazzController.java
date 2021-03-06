package br.usjt.rock.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.rock.model.bean.ApiEnum;

@RestController
@RequestMapping("/jazz/api/**")
public class JazzController extends RestClient {
	
	private static final String JAZZ_URL = "http://jazz.lucasduarte.club";

	public JazzController() {
		super(ApiEnum.JAZZ, JAZZ_URL);
	}
	
	@GetMapping
	public ResponseEntity<?> get(HttpServletRequest request) throws IOException {
		return getRequest(request);
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody String body, HttpServletRequest request) throws IOException {
		return postRequest(body, request);
	}
	
	@PutMapping
	public ResponseEntity<?> put(@RequestBody (required=false) String body, HttpServletRequest request) throws IOException {
		return putRequest(body, request);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(HttpServletRequest request) throws IOException {
		return deleteRequest(request);
	}
}