package com.yuhi.casonline.controller.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 返回页面
 * @author 李森林
 *
 */
@Controller
public class pageController {

	
	
	@RequestMapping("page")
	public String show(){
		return "index";
	}
	
}
