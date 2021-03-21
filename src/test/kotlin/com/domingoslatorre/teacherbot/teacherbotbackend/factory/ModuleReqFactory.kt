package com.domingoslatorre.teacherbot.teacherbotbackend.factory

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.ModuleReq

object ModuleReqFactory {
    fun moduleReq(
        title: String? = "Module 1",
        objective: String? = "Objective ...",
        position: Int? = 1,
    ) = ModuleReq(title, objective, position)

    fun moduleReq2() = this.moduleReq(title = "Module 2", position = 2)
    fun moduleReq3() = this.moduleReq(title = "Module 3", position = 3)
}
