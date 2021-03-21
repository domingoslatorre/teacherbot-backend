package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.CourseDto
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class CourseGetAllTest(@Autowired override val restTemplate: TestRestTemplate) : CourseIntegrationTest() {

    @Test
    fun `GET all courses - empty list`() {
        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content shouldBe listOf<CourseDto>()
        }
    }

    @Test
    fun `GET all courses - three registered`() {
        postCourse(CourseReqFactory.courseReq(name = "Course 1", acronym = "CO1"))
        postCourse(CourseReqFactory.courseReq(name = "Course 2", acronym = "CO2"))
        postCourse(CourseReqFactory.courseReq(name = "Course 3", acronym = "CO3"))

        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content?.size shouldBe 3
        }
    }
}
