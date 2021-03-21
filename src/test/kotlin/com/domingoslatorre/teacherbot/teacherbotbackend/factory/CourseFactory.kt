package com.domingoslatorre.teacherbot.teacherbotbackend.factory

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Course
import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Module
import java.util.UUID

object CourseFactory {
    fun course(
        id: UUID = UUID.randomUUID(),
        name: String = "Course name",
        acronym: String = "CN1",
        description: String = "Course description ...",
        modules: MutableSet<Module> = mutableSetOf(),
    ) = Course(id, name, acronym, description, modules)
}

object ModuleFactory {
    fun module(
        id: UUID = UUID.randomUUID(),
        title: String = "Module title",
        objective: String = "Module objective",
        position: Int = 1,
    ) = Module(id, title, objective, position)
}
