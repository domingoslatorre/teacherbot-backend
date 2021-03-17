package com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests

import javax.validation.constraints.*

data class CourseReq(
    @field:NotNull @field:NotBlank val name: String?,
    @field:NotNull @field:NotBlank @field:Size(min = 2, max = 5) val acronym: String?,
    @field:NotNull @field:NotBlank val description: String?,
)