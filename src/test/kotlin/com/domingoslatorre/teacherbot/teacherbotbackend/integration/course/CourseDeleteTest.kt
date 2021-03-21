package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class CourseDeleteTest(@Autowired override val restTemplate: TestRestTemplate) : CourseIntegrationTest() {

    @Test
    fun `DELETE course`() {
        val postRes = postCourse(CourseReqFactory.courseReq())
        deleteCourse(postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.NO_CONTENT
        }
    }

    @Test
    fun `DELETE course - NotFound`() {
        deleteCourseProblemDetail(UUID.randomUUID()).apply {
            statusCode shouldBe HttpStatus.NOT_FOUND
            body?.title shouldBe "Resource not found"
        }
    }
}
