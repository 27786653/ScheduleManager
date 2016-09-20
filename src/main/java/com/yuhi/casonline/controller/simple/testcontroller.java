package com.yuhi.casonline.controller.simple;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuhi.casonline.service.simple.MyService;
/**
 * 返回json
 * @author 李森林
 *
 */
@RestController
@RequestMapping("indexs")
public class testcontroller {
	@Resource
	private MyService service;
	
	@RequestMapping("{id}")
	public String show(@PathVariable("id")String id){
		return id+"===="+service.getThisTime();
	}
	
}
