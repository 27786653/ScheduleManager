package com.yuhi.casonline.listen.simple;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuhi.casonline.filter.simple.MyFilter;
/**
 * session监听器
 * @author 李森林
 *
 */
@WebListener
public class MySessionListen implements HttpSessionListener {


	private Logger log=LoggerFactory.getLogger(MySessionListen.class);
	
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		log.info("sessionCreated");
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		log.info("sessionDestroyed");
	}

}
