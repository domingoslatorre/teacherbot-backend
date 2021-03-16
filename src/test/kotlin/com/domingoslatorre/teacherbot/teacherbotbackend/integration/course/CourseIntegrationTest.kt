package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.api.*
import com.domingoslatorre.teacherbot.teacherbotbackend.util.TestPage
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
abstract class CourseIntegrationTest() {
    protected val coursesUrl: String = "/courses"
    abstract val restTemplate: TestRestTemplate

    protected fun getCourses(): ResponseEntity<TestPage<CourseRes>> =
        restTemplate.getForEntity(coursesUrl, coursePageResParam)

    protected fun getCourseById(id: UUID): ResponseEntity<CourseRes> =
        restTemplate.getForEntity("$coursesUrl/$id", courseResParam)

    protected fun getCourseProblemDetail(id: UUID): ResponseEntity<ProblemDetail> =
        restTemplate.getForEntity("$coursesUrl/$id", problemDetailResParam)

    protected fun postCourse(courseReq: CourseReq): ResponseEntity<CourseRes> =
        restTemplate.postForEntity(coursesUrl, courseReq, courseResParam)

    protected fun putCourse(courseReq: CourseReq, id: UUID): ResponseEntity<CourseRes> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), courseParam)

    protected fun putCourseProblemDetail(courseReq: CourseReq, id: UUID): ResponseEntity<ProblemDetail> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.PUT, HttpEntity(courseReq, HttpHeaders.EMPTY), problemDetailParam)

    protected fun deleteCourse(id: UUID): ResponseEntity<Void> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.DELETE)

    protected fun deleteCourseProblemDetail(id: UUID): ResponseEntity<ProblemDetail> =
        restTemplate.exchange("$coursesUrl/$id", HttpMethod.DELETE, null, problemDetailParam)


}