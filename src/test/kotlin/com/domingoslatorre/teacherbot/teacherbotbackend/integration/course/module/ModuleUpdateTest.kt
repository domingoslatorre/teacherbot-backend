package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course.module

import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.ModuleReqFactory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class ModuleUpdateTest (@Autowired override val restTemplate: TestRestTemplate) : ModuleIntegrationTest() {

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
}
