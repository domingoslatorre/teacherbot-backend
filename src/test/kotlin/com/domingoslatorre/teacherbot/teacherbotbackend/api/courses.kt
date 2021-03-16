package com.domingoslatorre.teacherbot.teacherbotbackend.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@ActiveProfiles("test")
class CourseControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun getAllCourses() {
        mockMvc?.perform(get("/courses").accept(MediaType.APPLICATION_JSON))
            ?.andExpect(status().isOk)
    }

}