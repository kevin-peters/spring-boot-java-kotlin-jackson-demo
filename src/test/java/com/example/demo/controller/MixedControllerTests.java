package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.JavaValueObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MixedController.class)
public class MixedControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testWithJavaPojo() throws Exception {
		validateEndpoint(MixedController.Binding.PATH_WITH_JAVA_MAPPING);
	}

	@Test
	public void testWithKotlinPojo() throws Exception {
		validateEndpoint(MixedController.Binding.PATH_WITH_KOTLIN_MAPPING);
	}

	private void validateEndpoint(final String path) throws Exception, JsonProcessingException, IOException,
			JsonParseException, JsonMappingException, UnsupportedEncodingException {
		final ResultActions result = mvc.perform(post(path).content(mapper.writeValueAsString(new JavaValueObject())));
		result.andExpect(status().isOk());

		final JavaValueObject expected = new JavaValueObject();
		expected.setValue(MixedController.RETURN_VALUE);

		final JavaValueObject output = mapper.readValue(result.andReturn().getResponse().getContentAsString(),
				JavaValueObject.class);
		assertThat(output).isEqualTo(expected);
	}

}
