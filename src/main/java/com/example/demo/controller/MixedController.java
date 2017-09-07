package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JavaValueObject;
import com.example.demo.model.KotlinDataClass;

@RestController
public class MixedController {

	static final String RETURN_VALUE = "changed";

	static class Binding {
		public static final String PATH_WITH_JAVA_MAPPING = "/java";
		public static final String PATH_WITH_KOTLIN_MAPPING = "/kotlin";
	}

	@RequestMapping(path = Binding.PATH_WITH_JAVA_MAPPING, method = RequestMethod.POST)
	public JavaValueObject javaMappingEndpoint(final JavaValueObject input) {
		input.setValue(RETURN_VALUE);
		return input;
	}

	@RequestMapping(path = Binding.PATH_WITH_KOTLIN_MAPPING, method = RequestMethod.POST)
	public KotlinDataClass kotlinMappingEndpoint(final KotlinDataClass input) {
		input.setValue(RETURN_VALUE);
		return input;
	}

}
