package br.usjt.rock.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import br.usjt.rock.model.bean.Jazz;
import br.usjt.rock.resource.JazzController;

public class JazzInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	Jazz jazzModel = new Jazz();
	JazzController jazzController;
	
	//TODO: Validar Login e Senha enviado na request
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = new UrlPathHelper().getPathWithinApplication(request);
		
		if (!jazzModel.validatePath(path.split("/")[3])) {
			System.out.println("Not found the path you want to request");
			response.sendError(404, "Path not found in Jazz application");
			return false;
		}
		//if returned false, we need to make sure 'response' is sent
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Request URL::" + request.getRequestURL().toString()
				+ " Sent to Handler :: Current Time=" + System.currentTimeMillis());
		//we can add attributes in the modelAndView and use that in the view page
	}

}
