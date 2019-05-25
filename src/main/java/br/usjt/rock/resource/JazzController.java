package br.usjt.rock.resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

//TODO: Deixar generico get e post
@RestController
@RequestMapping("/jazz/api/**")
public class JazzController {
	private static String JAZZ_URL = "http://jazz.lucasduarte.club";
	
	@GetMapping
	public ResponseEntity<?> getRequest(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception{
	
		System.out.println("Entrei no get");
		String path = new UrlPathHelper().getPathWithinApplication(request);
		path = path.replaceAll("/jazz", "");
		
		
		String urlRequest  = JAZZ_URL + path;
		
		System.out.println(urlRequest);
		
		URL obj = new URL(urlRequest);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");


		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer serverResponse = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			serverResponse.append(inputLine);
		}
		in.close();
		//print result
		System.out.println(response.toString());
		return ResponseEntity.status(HttpStatus.OK).body(serverResponse);	
	}

}
