package com.cafe24.jblog.config.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cafe24.jblog.security.AuthAdminInterceptor;

@Configuration
@EnableWebMvc
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public AuthAdminInterceptor authAdminInterceptor() {
		return new AuthAdminInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry
		.addInterceptor(authAdminInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/user/**")
		.excludePathPatterns("/assets/**");
	}
}
