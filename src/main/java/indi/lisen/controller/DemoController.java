package indi.lisen.controller;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import indi.lisen.service.DemoService;

@Controller
public class DemoController {

	@Resource
	DemoService demoService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		LoggerFactory.getLogger(this.getClass()).debug("hello world!");
		return demoService.getHello();
	}

	@RequestMapping("/dbtest")
	@ResponseBody
	public String dbTest() {
		demoService.dbTest();
		return "this is a dbtest";
	}
}
