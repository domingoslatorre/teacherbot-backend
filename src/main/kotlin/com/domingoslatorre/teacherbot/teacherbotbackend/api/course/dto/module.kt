package com.domingoslatorre.teacherbot.teacherbotbackend.api.course.dto

import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.model.Module
import java.util.*
import javax.validation.constraints.*

data class ModuleReq(
    @field:NotNull @field:NotBlank val title: String?,
    @field:Min(value = 1) val order: Int?,
)

data class ModuleRes(
    val id: UUID,
    val title: String,
    val order: Int,
)

fun Module.asResp() = ModuleRes(id, title, order)
