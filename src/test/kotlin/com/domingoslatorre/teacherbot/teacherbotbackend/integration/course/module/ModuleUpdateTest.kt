package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.module

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.ModuleReqFactory
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class ModuleUpdateTest(@Autowired override val restTemplate: TestRestTemplate) : ModuleIntegrationTest() {

    @Test
    fun `PUT module`() {
        val courseRes = postCourse(CourseReqFactory.courseReq()).body!!
        val moduleReq = ModuleReqFactory.moduleReq()
        val moduleRes = postModule(courseRes.id, moduleReq).body!!

        val moduleReqEdit = moduleReq.copy(title = "Module Edited", objective = "Module objetive Edited", position = 2)

        putModule(courseRes.id, moduleRes.id, moduleReqEdit).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe moduleRes.id
                title shouldBe moduleReqEdit.title
                objective shouldBe moduleReqEdit.objective
                position shouldBe moduleReqEdit.position
            }
        }

        getModuleById(courseRes.id, moduleRes.id).apply {
            statusCode shouldBe HttpStatus.OK
            body!!.apply {
                id shouldBe moduleRes.id
                title shouldBe moduleReqEdit.title
                objective shouldBe moduleReqEdit.objective
                position shouldBe moduleReqEdit.position
            }
        }
    }

    @Test
    fun `PUT module - same title`() {
        val courseRes = postCourse(CourseReqFactory.courseReq()).body!!

        val moduleReq1 = ModuleReqFactory.moduleReq(title = "Module 1")
        postModule(courseRes.id, moduleReq1).body!!

        val moduleReq2 = ModuleReqFactory.moduleReq(title = "Module 2")
        val moduleRes2 = postModule(courseRes.id, moduleReq2).body!!

        val moduleReqEdit = moduleReq2.copy(title = moduleReq1.title!!)

        putModuleProblemDetail(courseRes.id, moduleRes2.id, moduleReqEdit).apply {
            statusCode shouldBe HttpStatus.CONFLICT
            body!!.title shouldBe "Module already exists with title ${moduleReqEdit.title} course id ${courseRes.id}"
        }

        getModuleById(courseRes.id, moduleRes2.id).body!!.apply {
            id.shouldNotBeNull()
            title shouldBe moduleReq2.title
            objective shouldBe moduleReq2.objective
            position shouldBe moduleReq2.position
        }
    }
}
