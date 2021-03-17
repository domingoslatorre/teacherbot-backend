package com.domingoslatorre.teacherbot.teacherbotbackend.api.course.model

import java.util.*
import javax.persistence.*

class ModuleAlreadyExists : RuntimeException()

@Entity
class Course(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val acronym: String,
    val description: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    val modules: MutableList<Module> = mutableListOf(),
) {
    fun update(name: String, description: String, acronym: String) = Course(
        id = id,
        name = name,
        acronym = acronym,
        description = description,
        modules = modules
    )

    fun addModule(title: String, order: Int) {
        if(modules.firstOrNull { it.title == title } != null)
            throw ModuleAlreadyExists()
        modules.add(Module(title = title, order = order))
    }

}

