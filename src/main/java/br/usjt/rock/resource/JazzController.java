package br.usjt.rock.resource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/jazz/api/**")
public class JazzController {

	// TODO: Pegar de variavel de ambiente
	// TODO: Deixar variavel nome da aplicacao
	
	private static String JAZZ_URL = "http://jazz.lucasduarte.club";
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping
	public ResponseEntity<?> getRequest(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String param = "?";
		Map<String, String[]> parameters = request.getParameterMap();

		// Get Parameters of String
		for (Object key : parameters.keySet()) {
			String keyStr = (String) key;
			String[] value = (String[]) parameters.get(keyStr);
			param += "" + ((String) key + "=" + String.join("", value) + "&");
		}
		param = param.substring(0, param.length() - 1);

		String path = new UrlPathHelper().getPathWithinApplication(request);
		path = path.replaceAll("/jazz", "");
		String urlRequest = JAZZ_URL + path;

		// Check if params exists
		if (param != "?") {
			urlRequest = JAZZ_URL + path + param;
		}

		try {
			ResponseEntity<String> responseServer = restTemplate.getForEntity(urlRequest, String.class);
			JsonNode formatedJson = mapper.readTree(responseServer.getBody());
			return ResponseEntity.status(responseServer.getStatusCode()).body(formatedJson);

		} catch (HttpClientErrorException e) {
			JsonNode formatedJson = mapper.readTree(e.getResponseBodyAsString());
			return ResponseEntity.status(e.getStatusCode()).body(formatedJson);
		}
	}

	@PostMapping
	public ResponseEntity<?> postRequest(@RequestBody String body, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String path = new UrlPathHelper().getPathWithinApplication(request);
		path = path.replaceAll("/jazz", "");
		String urlRequest = JAZZ_URL + path;

		JsonNode formatedJson = mapper.readTree(body);
		try {
			String responseServer = restTemplate.postForObject(urlRequest, formatedJson, String.class);
			JsonNode formatedResponse = mapper.readTree(responseServer);
			return ResponseEntity.status(response.getStatus()).body(formatedResponse);

		} catch (HttpClientErrorException e) {
			JsonNode formatedResponse = mapper.readTree(e.getResponseBodyAsString());
			return ResponseEntity.status(response.getStatus()).body(formatedResponse);
		}
	}

	@PutMapping
	public ResponseEntity<?> putRequest(@RequestBody (required=false) String body, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String path = new UrlPathHelper().getPathWithinApplication(request);
		path = path.replaceAll("/jazz", "");
		String urlRequest = JAZZ_URL + path;
		
		System.out.println(urlRequest);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);	
			
			ResponseEntity<String> responseServer = restTemplate.exchange(urlRequest, HttpMethod.PUT, requestEntity,
					String.class);
			
			JsonNode formatedJson;
			try {
				formatedJson = mapper.readTree(responseServer.getBody());
			} catch (Exception e) {
				System.out.println("Entrou exception");
				ObjectNode objectNode1 = mapper.createObjectNode();
				objectNode1.put("Status", "True");
				return ResponseEntity.status(responseServer.getStatusCode()).body(objectNode1);
			}
			return ResponseEntity.status(responseServer.getStatusCode()).body(formatedJson);

		} catch (HttpClientErrorException e) {
			JsonNode formatedResponse = mapper.readTree(e.getResponseBodyAsString());
			return ResponseEntity.status(response.getStatus()).body(formatedResponse);
		}
	}

}