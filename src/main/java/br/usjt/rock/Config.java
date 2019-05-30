package br.usjt.rock;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.usjt.rock.interceptors.JazzInterceptor;
import br.usjt.rock.interceptors.LoginInterceptor;
import br.usjt.rock.interceptors.MaestroInterceptor;

//Classe para adicionar os interceptors
@Configuration
@EnableWebMvc
public class Config implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//TODO: Verificar a possibilidade de validar todos os interceptors
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/api/login/", "/maestro/api/torcedor");
		registry.addInterceptor(new JazzInterceptor()).addPathPatterns("/jazz/**");
		registry.addInterceptor(new MaestroInterceptor()).addPathPatterns("/maestro/**");
		//.excludePathPatterns("/login", "/", "/fazerLogin")
	}
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }
}