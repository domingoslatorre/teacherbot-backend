package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.api.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.*
import org.springframework.http.*
import java.util.*

class CourseGetByIdTest (
    @Autowired override val restTemplate: TestRestTemplate
) : CourseIntegrationTest() {

    @Test
    fun `GET course by id`() {
        val courseReq = CourseReq("Lógica de Programação 2", "LG2", "Lógica de p...")
        val postRes = postCourse(courseReq)

        getCourseById(postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body?.apply {
                id shouldBe postRes.body?.id
                name shouldBe courseReq.name
                acronym shouldBe courseReq.acronym
                description shouldBe courseReq.description
            }
        }
    }

    @Test
    fun `GET course by id - NotFound`() {
        getCourseProblemDetail(UUID.randomUUID()).apply {
            statusCode shouldBe HttpStatus.NOT_FOUND
            body?.title shouldBe "Resource not found"
        }
    }

}