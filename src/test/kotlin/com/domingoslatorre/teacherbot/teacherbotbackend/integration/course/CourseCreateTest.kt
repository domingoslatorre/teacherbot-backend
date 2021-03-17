package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.common.*
import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.*
import org.springframework.http.*

class CourseCreateTest (
    @Autowired override val restTemplate: TestRestTemplate
) : CourseIntegrationTest() {

    @Test
    fun `POST course`() {
        val courseReq = CourseReq("Lógica de Programação 5", "LG5", "Lógica de p...")
        postCourse(courseReq).apply {
            statusCode shouldBe HttpStatus.CREATED
            body?.apply {
                name shouldBe courseReq.name
                acronym shouldBe courseReq.acronym
                description shouldBe courseReq.description
            }
        }
    }

    @Test
    fun `POST course - name already exists - Conflict`() {
        val courseReq1 = CourseReq("Lógica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Lógica de Programação 1", "LG2", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetailResParam)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

    @Test
    fun `POST course - acronym name already exists - Conflict`() {
        val courseReq1 = CourseReq("Lógica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Lógica de Programação 2", "LG1", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetailResParam)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

}