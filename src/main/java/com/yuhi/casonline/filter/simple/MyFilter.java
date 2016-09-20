package com.yuhi.casonline.filter.simple;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * 声明一个过滤器
 * filterName 过滤器名
 * urlPatterns 过滤路径
 * @author 李森林
 *
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {
	
	private Logger log=LoggerFactory.getLogger(MyFilter.class);
	
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("MyFilter is destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse respnose,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("MyFilter is doFilter");
		chain.doFilter(request, respnose);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		log.info("MyFilter is init");
	}

}
