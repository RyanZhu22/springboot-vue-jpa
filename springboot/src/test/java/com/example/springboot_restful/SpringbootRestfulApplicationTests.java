package com.example.springboot_restful;

import com.example.springboot_restful.model.dto.dict.DictUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootRestfulApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
	}

	@Test
	public void testUpdate() throws Exception {
		DictUpdateDto dictUpdateDto = new DictUpdateDto();
		dictUpdateDto.setCode("test");
		dictUpdateDto.setValue("test");
		dictUpdateDto.setType("test");

		mockMvc.perform(put("http://localhost:8080/dict")
				.content(objectMapper.writeValueAsString(dictUpdateDto))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

}
