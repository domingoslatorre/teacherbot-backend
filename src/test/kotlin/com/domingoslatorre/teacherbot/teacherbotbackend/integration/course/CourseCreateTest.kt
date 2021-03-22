package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.common.ProblemDetail
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CourseCreateTest(@Autowired override val restTemplate: TestRestTemplate) : CourseIntegrationTest() {

    @Test
    fun `POST course`() {
        val courseReq = CourseReqFactory.courseReq()
        postCourse(courseReq).apply {
            statusCode shouldBe HttpStatus.CREATED
            body?.apply {
                name shouldBe courseReq.name
                acronym shouldBe courseReq.acronym
                description shouldBe courseReq.description
            }
            headers.location!!.path shouldBe "/courses/${this.body!!.id}"
        }
    }

    @Test
    fun `POST course - name already exists - Conflict`() {
        val courseReq1 = CourseReqFactory.courseReq(name = "Course name")
        val courseReq2 = CourseReqFactory.courseReq(name = "Course name")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(
            coursesUrl, courseReq2, problemDetailResParam
        )
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Course already exists with name ${courseReq2.name} and acronym ${courseReq2.acronym}"
        }
    }

    @Test
    fun `POST course - acronym name already exists - Conflict`() {
        val courseReq1 = CourseReqFactory.courseReq(name = "Course name 1", acronym = "AC1")
        val courseReq2 = CourseReqFactory.courseReq(name = "Course name 2", acronym = "AC1")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(
            coursesUrl, courseReq2, problemDetailResParam
        )
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Course already exists with name ${courseReq2.name} and acronym ${courseReq2.acronym}"
        }
    }
}
