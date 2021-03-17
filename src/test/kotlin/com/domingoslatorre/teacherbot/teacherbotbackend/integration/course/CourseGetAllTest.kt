package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.dto.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class CourseGetAllTest (
    @Autowired override val restTemplate: TestRestTemplate
) : CourseIntegrationTest() {

    @Test
    fun `GET all courses - empty list`() {
        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content shouldBe listOf<CourseRes>()
        }
    }

    @Test
    fun `GET all courses - three registered`() {
        postCourse(CourseReq("Lógica 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica 2", "LG2", "Lógica de p..."))
        postCourse(CourseReq("Lógica 3", "LG3", "Lógica de p..."))

        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content?.size shouldBe 3
        }
    }
}