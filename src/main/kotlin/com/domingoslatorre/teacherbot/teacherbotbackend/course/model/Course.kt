package com.domingoslatorre.teacherbot.teacherbotbackend.course.model

import java.lang.RuntimeException
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

data class ModuleAlreadyExists(override val message: String) : RuntimeException(message)

@Entity
class Course(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val acronym: String,
    val description: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "course_id")
    val modules: MutableSet<Module> = mutableSetOf(),
) {
    fun update(name: String, description: String, acronym: String) = Course(
        id = id,
        name = name,
        acronym = acronym,
        description = description,
        modules = modules
    )

    fun addModule(title: String, objective: String, position: Int): Result<Module> =
        Module(title = title, objective = objective, position = position).let {
            if (modules.add(it)) Result.success(it)
            else Result.failure(ModuleAlreadyExists("Module with title $title already exists in course $id"))
        }
}
