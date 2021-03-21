package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class CourseUpdateTest(@Autowired override val restTemplate: TestRestTemplate) : CourseIntegrationTest() {

    @Test
    fun `PUT course`() {
        val courseReq = CourseReqFactory.courseReq()
        val postRes = postCourse(courseReq)
        val courseEditReq = courseReq.copy(name = "Course 2", acronym = "CO2")
        putCourse(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe postRes.body!!.id
                name shouldBe courseEditReq.name
                acronym shouldBe courseEditReq.acronym
                description shouldBe courseEditReq.description
            }
        }
    }

    @Test
    fun `PUT course - different name, different acronym - Ok`() {
        postCourse(CourseReqFactory.courseReq())
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Course 4", acronym = "CO4")

        putCourse(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe postRes.body!!.id
                name shouldBe courseEditReq.name
                acronym shouldBe courseEditReq.acronym
                description shouldBe courseEditReq.description
            }
        }
    }

    @Test
    fun `PUT course - different name, same acronym - Ok`() {
        postCourse(CourseReqFactory.courseReq())
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Course 4")

        putCourse(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe postRes.body!!.id
                name shouldBe courseEditReq.name
                acronym shouldBe courseEditReq.acronym
                description shouldBe courseEditReq.description
            }
        }
    }

    @Test
    fun `PUT course - same name, different acronym - Ok`() {
        postCourse(CourseReqFactory.courseReq())
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(acronym = "CO4")

        putCourse(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe postRes.body!!.id
                name shouldBe courseEditReq.name
                acronym shouldBe courseEditReq.acronym
                description shouldBe courseEditReq.description
            }
        }
    }

    @Test
    fun `PUT course - different name (already exists), same acronym - Conflict`() {
        val postRes1 = postCourse(CourseReqFactory.courseReq()).body!!
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = postRes1.name)

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

    @Test
    fun `PUT course - same name, different acronym (already exists) - Conflict`() {
        val postRes1 = postCourse(CourseReqFactory.courseReq()).body!!
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(acronym = postRes1.acronym)

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

    @Test
    fun `PUT course - different name (already exists), different acronym (already exists) - Conflict`() {
        val postRes1 = postCourse(CourseReqFactory.courseReq()).body!!
        postCourse(CourseReqFactory.courseReq2())

        val courseReq = CourseReqFactory.courseReq3()
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = postRes1.name, acronym = postRes1.acronym)

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }
}
