package com.domingoslatorre.teacherbot.teacherbotbackend.integration

import com.domingoslatorre.teacherbot.teacherbotbackend.api.*
import com.domingoslatorre.teacherbot.teacherbotbackend.util.TestPage
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class CoursesIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort private val port: Int
) {
    val coursesUrl: String = "http://localhost:$port/courses"
    val coursePageResParam = object : ParameterizedTypeReference<ResponseEntity<TestPage<CourseRes>>>() {}
    val courseResParam = object : ParameterizedTypeReference<ResponseEntity<CourseRes>>() {}
    val courseParam = object : ParameterizedTypeReference<CourseRes>() {}
    val problemDetailResParam = object : ParameterizedTypeReference<ResponseEntity<ProblemDetail>>() {}
    val problemDetailParam = object : ParameterizedTypeReference<ProblemDetail>() {}

    private fun getCourses(): ResponseEntity<TestPage<CourseRes>> =
        restTemplate.getForEntity(coursesUrl, coursePageResParam)

    private fun getCourseById(id: UUID): ResponseEntity<CourseRes> =
        restTemplate.getForEntity("$coursesUrl/$id", courseResParam)

    private fun postCourse(courseReq: CourseReq): ResponseEntity<CourseRes> =
        restTemplate.postForEntity(coursesUrl, courseReq, courseResParam)

    private fun putCourse(courseReq: CourseReq, id: UUID): ResponseEntity<CourseRes> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), courseParam)

    private fun putCourseProblemDetail(courseReq: CourseReq, id: UUID): ResponseEntity<ProblemDetail> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), problemDetailParam)


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

    @Test
    fun `GET course by id`() {
        val courseCreate = CourseReq("Lógica de Programação 2", "LG2", "Lógica de p...")
        val postRes = postCourse(courseCreate)

        getCourseById(postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.OK
            body?.apply {
                id shouldBe postRes.body?.id
                name shouldBe courseCreate.name
                acronym shouldBe courseCreate.acronym
                description shouldBe courseCreate.description
            }
        }
    }

    @Test
    fun `GET course by id - NotFound`() {
        val uuid = UUID.randomUUID()
        val response: ResponseEntity<ProblemDetail> = restTemplate.getForEntity("$coursesUrl/$uuid", problemDetailResParam)
        response.statusCode shouldBe HttpStatus.NOT_FOUND
    }

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
    fun `POST course - same name - conflict`() {
        val courseReq1 = CourseReq("Lógica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Lógica de Programação 1", "LG2", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetailResParam)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
        }
    }

    @Test
    fun `POST course - same acronym - conflict`() {
        val courseReq1 = CourseReq("Lógica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Lógica de Programação 2", "LG1", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetailResParam)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
        }
    }

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
    fun `PUT course - different name, different acronym - ok`() {
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
    fun `PUT course - different name, same acronym - ok`() {
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
    fun `PUT course - same name, different acronym - ok`() {
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
    fun `PUT course - different name (already exists), same acronym - conflict`() {
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 2", acronym = "LG3")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body!!.apply {
                title shouldBe "Resource already exists"
            }
        }
    }

    @Test
    fun `PUT course - same name, different acronym (already exists) - conflict`() {
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 3", acronym = "LG2")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body!!.apply {
                title shouldBe "Resource already exists"
            }
        }
    }

    @Test
    fun `PUT course - different name (already exists), different acronym (already exists) - conflict`() {
        postCourse(CourseReq("Lógica de Programação 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Lógica de Programação 2", "LG2", "Lógica de p..."))

        val courseReq = CourseReq("Lógica de Programação 3", "LG3", "Lógica de p...")
        val postRes = postCourse(courseReq)

        val courseEditReq = courseReq.copy(name = "Lógica de Programação 1", acronym = "LG1")

        putCourseProblemDetail(courseEditReq, postRes.body!!.id).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body!!.apply {
                title shouldBe "Resource already exists"
            }
        }
    }



}
