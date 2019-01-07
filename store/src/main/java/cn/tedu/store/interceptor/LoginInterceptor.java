package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object hanler) throws Exception{
		HttpSession session = request.getSession();
		System.out.println("拦截器"+session.getAttribute("uid"));
		if (session.getAttribute("uid") == null) {
			response.sendRedirect("../web/login.html");
			return false;
		} else {
			return true;
		}
		
	}

}
