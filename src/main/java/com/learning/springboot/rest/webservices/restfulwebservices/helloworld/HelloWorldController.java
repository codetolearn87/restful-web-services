package com.learning.springboot.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	//GET
	//URI - /hello-world
	//method - "Hello World"
	//Alternative to @RequestMapping is @GetMapping(path="/hello-world") or @GetMapping("/hello-world") 
	@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	public String helloWorld(){
		return "Hello World";
	}
	
	// Example returning a bean
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	// Example using a path variable or path parameters
	@GetMapping("/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	// Example of internationalization
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
	//public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, locale);
		
	//	return "Good Morning";
	}
	
}
