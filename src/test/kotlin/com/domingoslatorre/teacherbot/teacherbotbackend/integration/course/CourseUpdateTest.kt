package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.*
import org.springframework.http.*

class CourseUpdateTest (
    @Autowired override val restTemplate: TestRestTemplate
) : CourseIntegrationTest() {

    @Test
    fun `PUT course`() {
        val courseReq = CourseReq("Lógica de Programação 5", "LG5", "Lógica de p...")
        val postRes = postCourse(courseReq)
        val courseEditReq = courseReq.copy(name = "Lógica de Programação 4", acronym = "LG4", description = "...")
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
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 5", acronym = "LG5")

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
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 5", acronym = "LG3")

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
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 3", acronym = "LG5")

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
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 2", acronym = "LG3")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

    @Test
    fun `PUT course - same name, different acronym (already exists) - Conflict`() {
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 3", acronym = "LG2")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

    @Test
    fun `PUT course - different name (already exists), different acronym (already exists) - Conflict`() {
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 1", acronym = "LG1")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Resource already exists"
        }
    }

}