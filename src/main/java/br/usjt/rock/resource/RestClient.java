package br.usjt.rock.resource;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.usjt.rock.model.bean.ApiEnum;

public abstract class RestClient {

	private ApiEnum api;
	private String endPoint;
	private String urlRequest;
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();

	public RestClient(ApiEnum api, String endPoint) {
		this.api = api;
		this.endPoint= endPoint;
	}
	
	private void replaceInPathAndSetUrlRequestValue(HttpServletRequest request) {
		String path = new UrlPathHelper().getPathWithinApplication(request);
		path = validateApiEnum(path);
		this.urlRequest = this.endPoint + path;
	}
	
	private String replaceInPath(HttpServletRequest request) {
		String path = new UrlPathHelper().getPathWithinApplication(request);
		return validateApiEnum(path);
	}
	
	private String validateApiEnum(String path) {
		String result = "";
		if (ApiEnum.MAESTRO.equals(this.api)) {
			result = path.replace("/maestro", "");
		} else if (ApiEnum.JAZZ.equals(this.api)) {
			result = path.replace("/jazz", "");
		}
		return result;
	}
	
	private String generateUrlRequestWithParams(HttpServletRequest request) {
		String param = "?";
		Map<String, String[]> parameters = request.getParameterMap();

		// Get Parameters of String
		for (Object key : parameters.keySet()) {
			String keyStr = (String) key;
			String[] value = (String[]) parameters.get(keyStr);
			param += "" + ((String) key + "=" + String.join("", value) + "&");
		}
		param = param.substring(0, param.length() - 1);
		replaceInPathAndSetUrlRequestValue(request);
		return param;
	}
	
	private void validateParamsInRequest(HttpServletRequest request, String param) {
		String path = replaceInPath(request);
		if (param != "?") {
			this.urlRequest = this.endPoint + path + param;
		}
	}
	
	private HttpHeaders getHeaderDefaultContentTypeJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	private HttpEntity<?> getHttpEntity(MultiValueMap<String, String> headers) {
		return new HttpEntity<>(headers); 
	}
	
	private HttpEntity<?> getHttpEntity(MultiValueMap<String, String> headers, JsonNode body) {
		return new HttpEntity<>(body, headers); 
	}
	
	private ResponseEntity<String> request(HttpMethod method, HttpEntity<?> requestEntity) throws HttpClientErrorException {
		return restTemplate.exchange(this.urlRequest, method, requestEntity, String.class);
	}

	protected ResponseEntity<?> getRequest(HttpServletRequest request) throws IOException {
		
		ResponseEntity<String> response = null;
		
		String param = generateUrlRequestWithParams(request);
		validateParamsInRequest(request, param);

		System.out.println("aqui ->"+ this.urlRequest);
		try {
			response = request(HttpMethod.GET, getHttpEntity(getHeaderDefaultContentTypeJson()));
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(mapper.readTree(e.getResponseBodyAsString()));
		}
		
		return ResponseEntity.status(response.getStatusCode()).body(mapper.readTree(response.getBody()));
	}

	protected ResponseEntity<?> postRequest(String body, HttpServletRequest request) throws IOException {
		
		ResponseEntity<String> response = null;

		replaceInPathAndSetUrlRequestValue(request);
		
		try {
			response = request(HttpMethod.POST, getHttpEntity(getHeaderDefaultContentTypeJson(), mapper.readTree(body)));
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(mapper.readTree(e.getResponseBodyAsString()));
		}
		
		return ResponseEntity.status(response.getStatusCode()).body(mapper.readTree(response.getBody()));
	}

	protected ResponseEntity<?> putRequest(String body, HttpServletRequest request) throws IOException {

		ResponseEntity<String> response = null;
		
		replaceInPathAndSetUrlRequestValue(request);

		try {
			response = request(HttpMethod.PUT, getHttpEntity(getHeaderDefaultContentTypeJson(), mapper.readTree(body)));
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(mapper.readTree(e.getResponseBodyAsString()));
		}
		
		return ResponseEntity.status(response.getStatusCode()).body(mapper.readTree(response.getBody()));
	}
	
	protected ResponseEntity<?> deleteRequest(HttpServletRequest request) throws IOException {

		ResponseEntity<String> response = null;

		replaceInPathAndSetUrlRequestValue(request);

		try {
			response = request(HttpMethod.DELETE, getHttpEntity(getHeaderDefaultContentTypeJson()));
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(mapper.readTree(e.getResponseBodyAsString()));
		}
		
		return ResponseEntity.status(response.getStatusCode()).body(mapper.readTree(response.getBody()));
	}
}