package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.module

import com.domingoslatorre.teacherbot.teacherbotbackend.common.ProblemDetail
import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.ModuleReq
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.ModuleDto
import com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.CourseIntegrationTest
import com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.moduleListResParam
import com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.moduleParam
import com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.moduleResParam
import com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.problemDetailParam
import java.util.UUID
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

abstract class ModuleIntegrationTest : CourseIntegrationTest() {
    abstract override val restTemplate: TestRestTemplate

    private fun modulesUrl(courseId: UUID) =
        restTemplate.restTemplate.uriTemplateHandler.expand("/courses/{courseId}/modules", courseId).toString()

    private fun moduleUrl(courseId: UUID, moduleId: UUID) = restTemplate.restTemplate
        .uriTemplateHandler.expand("/courses/{courseId}/modules/{moduleId}", courseId, moduleId).toString()

    protected fun getModules(courseId: UUID): ResponseEntity<List<ModuleDto>> =
        restTemplate.getForEntity(modulesUrl(courseId), moduleListResParam)

    protected fun getModuleById(courseId: UUID, moduleId: UUID): ResponseEntity<ModuleDto> =
        restTemplate.getForEntity(moduleUrl(courseId, moduleId), moduleResParam, courseId, moduleId)

    protected fun postModule(courseId: UUID, moduleReq: ModuleReq): ResponseEntity<ModuleDto> =
        restTemplate.postForEntity(modulesUrl(courseId), moduleReq, moduleResParam)

    protected fun postModuleProblemDetail(courseId: UUID, moduleReq: ModuleReq): ResponseEntity<ProblemDetail> =
        restTemplate.postForEntity(modulesUrl(courseId), moduleReq, problemDetailParam)

    protected fun putModule(courseId: UUID, moduleId: UUID, moduleReq: ModuleReq): ResponseEntity<ModuleDto> =
        restTemplate.exchange(
            moduleUrl(courseId, moduleId),
            HttpMethod.PUT,
            HttpEntity(moduleReq, HttpHeaders.EMPTY),
            moduleParam
        )

    protected fun putModuleProblemDetail(
        courseId: UUID,
        moduleId: UUID,
        moduleReq: ModuleReq
    ): ResponseEntity<ProblemDetail> =
        restTemplate.exchange(
            moduleUrl(courseId, moduleId),
            HttpMethod.PUT,
            HttpEntity(moduleReq, HttpHeaders.EMPTY),
            problemDetailParam
        )
}
