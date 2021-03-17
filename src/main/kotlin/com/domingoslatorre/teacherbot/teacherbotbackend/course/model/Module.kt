package com.domingoslatorre.teacherbot.teacherbotbackend.course.model

import java.util.*
import javax.persistence.*

@Entity
class Module(
    @Id
    val id: UUID = UUID.randomUUID(),
    val title: String,
    @Column(name = "module_order")
    val order: Int,
)