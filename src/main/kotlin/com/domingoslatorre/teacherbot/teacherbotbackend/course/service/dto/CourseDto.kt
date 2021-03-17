package com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Course
import java.util.*

data class CourseDto(
    val id: UUID,
    val name: String,
    val acronym: String,
    val description: String,
    val modules: List<ModuleDto>
)

fun Course.asDto() = CourseDto(id, name, acronym, description, modules.map { it.asDto() })