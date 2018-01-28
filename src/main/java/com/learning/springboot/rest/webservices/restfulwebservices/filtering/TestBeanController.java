package com.learning.springboot.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class TestBeanController {
	
	//filter - field1, field2
	@GetMapping("/test-bean")
	public MappingJacksonValue retrieveTestBean() {
		TestBean testBean = new TestBean("Amar", "Akbar", "Anthony");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("TestBeanFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(testBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	//filter - field2, field3
	@GetMapping("/all-test-beans")
	public MappingJacksonValue retrieveListOfTestBeans() {
		List<TestBean> list = Arrays.asList(new TestBean("Amar", "Akbar", "Anthony"),
				new TestBean("Amar2", "Akbar2", "Anthony2"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("TestBeanFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		
		return mapping;
	}
}
