package com.yuhi.casonline.service.simple.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.yuhi.casonline.service.simple.MyService;

@Service
public class MyServiceimpl implements MyService{
	
	@Override
	public String getThisTime(){
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	}
	
}
