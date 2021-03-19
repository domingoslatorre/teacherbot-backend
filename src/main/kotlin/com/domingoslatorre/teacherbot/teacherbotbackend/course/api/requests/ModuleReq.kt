@file:Suppress("unused")

package com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ModuleReq(
    @field:[NotNull NotBlank]
    val title: String?,
    @field:Min(value = 1)
    val order: Int?,
)
