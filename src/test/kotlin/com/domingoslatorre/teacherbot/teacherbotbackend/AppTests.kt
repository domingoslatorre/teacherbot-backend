package com.domingoslatorre.teacherbot.teacherbotbackend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@ActiveProfiles("test")
class AppTests {

	@Autowired
	private val mockMvc: MockMvc? = null

	@Test
	fun healthCheckEndpoint() {
		mockMvc?.perform(MockMvcRequestBuilders.get("/health"))
			?.andExpect(MockMvcResultMatchers.status().isOk)
	}
}
