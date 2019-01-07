package cn.tedu.store.configurer;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.LoginInterceptor;


@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> paht= new ArrayList<String>();
		paht.add("/user/**");
		paht.add("/web/**");
		
		List<String>  ex = new ArrayList<String>();
		ex.add("/user/reg.do");
		ex.add("/web/reg.html");		
		ex.add("/user/login.do");
		ex.add("/web/login.html");
		
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns(paht)
		.excludePathPatterns(ex);
		System.out.println("sdsadsadsadsadsadsad");
	}

	

}
