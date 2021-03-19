package com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CourseReq(
    @field:[NotNull NotBlank]
    val name: String?,
    @field:[NotNull NotBlank]
    @field:Size(min = 2, max = 5)
    val acronym: String?,
    @field:[NotNull NotBlank]
    val description: String?,
)
