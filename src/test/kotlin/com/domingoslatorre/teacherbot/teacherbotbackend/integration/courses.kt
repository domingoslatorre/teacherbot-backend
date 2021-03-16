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
    val problemDetail = object : ParameterizedTypeReference<ResponseEntity<ProblemDetail>>() {}

    private fun getCourses(): ResponseEntity<TestPage<CourseRes>> = restTemplate.getForEntity(coursesUrl, coursePageResParam)
    private fun getCourseById(id: UUID): ResponseEntity<CourseRes> = restTemplate.getForEntity("$coursesUrl/$id", courseResParam)
    private fun postCourse(courseReq: CourseReq): ResponseEntity<CourseRes> = restTemplate.postForEntity(coursesUrl, courseReq, courseResParam)


    @Test
    fun `GET all courses - empty list`() {
        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content shouldBe listOf<CourseRes>()
        }
    }

    @Test
    fun `GET all courses - three registered`() {
        postCourse(CourseReq("Loǵica 1", "LG1", "Lógica de p..."))
        postCourse(CourseReq("Loǵica 2", "LG2", "Lógica de p..."))
        postCourse(CourseReq("Loǵica 3", "LG3", "Lógica de p..."))

        getCourses().apply {
            statusCode shouldBe HttpStatus.OK
            body?.content?.size shouldBe 3
        }
    }

    @Test
    fun `GET course by id`() {
        val courseCreate = CourseReq("Loǵica de Programação 2", "LG2", "Lógica de p...")
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
        val response: ResponseEntity<ProblemDetail> = restTemplate.getForEntity("$coursesUrl/$uuid", problemDetail)
        response.statusCode shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `POST course`() {
        val courseCreate = CourseReq("Loǵica de Programação 5", "LG5", "Lógica de p...")
        postCourse(courseCreate).apply {
            statusCode shouldBe HttpStatus.CREATED
            body?.apply {
                name shouldBe courseCreate.name
                acronym shouldBe courseCreate.acronym
                description shouldBe courseCreate.description
            }
        }
    }

    @Test
    fun `POST course - same name - conflict`() {
        val courseReq1 = CourseReq("Loǵica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Loǵica de Programação 1", "LG2", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetail)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
        }
    }

    @Test
    fun `POST course - same acronym - conflict`() {
        val courseReq1 = CourseReq("Loǵica de Programação 1", "LG1", "Lógica de p...")
        val courseReq2 = CourseReq("Loǵica de Programação 2", "LG1", "Lógica de p...")
        postCourse(courseReq1)

        val response: ResponseEntity<ProblemDetail> = restTemplate.postForEntity(coursesUrl, courseReq2, problemDetail)
        response.apply {
            statusCode shouldBe HttpStatus.CONFLICT
        }
    }


}
