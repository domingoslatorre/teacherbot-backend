package com.domingoslatorre.teacherbot.teacherbotbackend.course.model

import java.util.*
import javax.persistence.*

@Entity
class Course(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val acronym: String,
    val description: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
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
}

