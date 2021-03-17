package com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests

import javax.validation.constraints.*

data class ModuleReq(
    @field:NotNull @field:NotBlank val title: String?,
    @field:Min(value = 1) val order: Int?,
)