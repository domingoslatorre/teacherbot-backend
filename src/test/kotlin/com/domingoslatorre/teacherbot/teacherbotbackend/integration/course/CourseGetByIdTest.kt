package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.util.UUID

class CourseGetByIdTest(@Autowired override val restTemplate: TestRestTemplate) : CourseIntegrationTest() {

    @Test
    fun `GET course by id`() {
        val courseReq = CourseReqFactory.courseReq()
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
        val id = UUID.randomUUID()
        getCourseProblemDetail(id).apply {
            statusCode shouldBe HttpStatus.NOT_FOUND
            body?.title shouldBe "Course not found with id $id"
        }
    }
}
