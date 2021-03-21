package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.module

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.ModuleReqFactory
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class ModuleCreateTest(@Autowired override val restTemplate: TestRestTemplate) : ModuleIntegrationTest() {

    @Test
    fun `POST module`() {
        val courseRes = postCourse(CourseReqFactory.courseReq()).body!!
        val moduleReq = ModuleReqFactory.moduleReq()
        val moduleRes = postModule(courseRes.id, moduleReq)

        moduleRes.apply {
            statusCode shouldBe HttpStatus.CREATED
            body!!.apply {
                id.shouldNotBeNull()
                title shouldBe moduleReq.title
                objective shouldBe moduleReq.objective
                position shouldBe moduleReq.position
            }
        }

        getModules(courseRes.id).body!! shouldHaveSize 1

        getModuleById(courseRes.id, moduleRes.body!!.id).body!!.apply {
            id.shouldNotBeNull()
            title shouldBe moduleReq.title
            objective shouldBe moduleReq.objective
            position shouldBe moduleReq.position
        }
    }

    @Test
    fun `POST module - title already exists - Conflict`() {
        val courseRes = postCourse(CourseReqFactory.courseReq()).body!!
        val moduleReq = ModuleReqFactory.moduleReq()
        postModule(courseRes.id, moduleReq)

        postModuleProblemDetail(courseRes.id, moduleReq).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body?.title shouldBe "Module already exists with title ${moduleReq.title} course id ${courseRes.id}"
        }
    }
}
