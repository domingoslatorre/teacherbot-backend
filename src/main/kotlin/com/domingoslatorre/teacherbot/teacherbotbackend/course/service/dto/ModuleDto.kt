package com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Module
import java.util.UUID

data class ModuleDto(
    val id: UUID,
    val title: String,
    val objective: String,
    val position: Int,
)

fun Module.asDto() = ModuleDto(id, title, objective, position)
