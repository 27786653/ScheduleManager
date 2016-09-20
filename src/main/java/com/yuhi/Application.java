package com.yuhi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration 
@SpringBootApplication
@ServletComponentScan 
@ComponentScan
public class Application extends SpringBootServletInitializer
{
	/**
	 * 服务入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(Application.class);
		app.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
	
}
