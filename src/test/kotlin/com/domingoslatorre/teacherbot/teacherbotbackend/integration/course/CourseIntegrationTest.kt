package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.common.ProblemDetail
import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.CourseDto
import com.domingoslatorre.teacherbot.teacherbotbackend.util.TestPage
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
abstract class CourseIntegrationTest {
    protected val coursesUrl: String = "/courses"
    abstract val restTemplate: TestRestTemplate

    protected fun getCourses(): ResponseEntity<TestPage<CourseDto>> =
            restTemplate.getForEntity(coursesUrl, coursePageResParam)

    protected fun getCourseById(id: UUID): ResponseEntity<CourseDto> =
            restTemplate.getForEntity("$coursesUrl/$id", courseResParam)

    protected fun getCourseProblemDetail(id: UUID): ResponseEntity<ProblemDetail> =
            restTemplate.getForEntity("$coursesUrl/$id", problemDetailResParam)

    protected fun postCourse(courseReq: CourseReq): ResponseEntity<CourseDto> =
            restTemplate.postForEntity(coursesUrl, courseReq, courseResParam)

    protected fun putCourse(courseReq: CourseReq, id: UUID): ResponseEntity<CourseDto> =
            restTemplate.exchange(
                    "$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), courseParam
            )

    protected fun putCourseProblemDetail(courseReq: CourseReq, id: UUID): ResponseEntity<ProblemDetail> =
            restTemplate.exchange(
                    "$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), problemDetailParam
            )

    protected fun deleteCourse(id: UUID): ResponseEntity<Void> =
            restTemplate.exchange("$coursesUrl/$id", HttpMethod.DELETE)

    protected fun deleteCourseProblemDetail(id: UUID): ResponseEntity<ProblemDetail> =
            restTemplate.exchange("$coursesUrl/$id", HttpMethod.DELETE, null, problemDetailParam)
}
