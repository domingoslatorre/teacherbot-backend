package com.domingoslatorre.teacherbot.teacherbotbackend.api.course.dto

import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.model.Course
import java.util.*
import javax.validation.constraints.*

data class CourseReq(
    @field:NotNull @field:NotBlank val name: String?,
    @field:NotNull @field:NotBlank @field:Size(min = 2, max = 5) val acronym: String?,
    @field:NotNull @field:NotBlank val description: String?,
)

data class CourseRes(
    val id: UUID,
    val name: String,
    val acronym: String,
    val description: String,
    val modules: List<ModuleRes>
)

fun Course.asResp() = CourseRes(id, name, acronym, description, modules.map { it.asResp() })