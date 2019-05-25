package br.usjt.rock;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.usjt.rock.interceptors.JazzInterceptor;

//Classe para adicionar os interceptors
@Configuration
public class Config implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//TODO: Verificar a possibilidade de validar todos os interceptors
		registry.addInterceptor(new JazzInterceptor()).
			addPathPatterns("/jazz/**").excludePathPatterns("/login", "/", "/fazerLogin");
	}
}
