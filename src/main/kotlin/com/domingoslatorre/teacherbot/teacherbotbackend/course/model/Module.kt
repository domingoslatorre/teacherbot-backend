package com.domingoslatorre.teacherbot.teacherbotbackend.course.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Module(
    @Id
    val id: UUID = UUID.randomUUID(),
    val title: String,
    @Column(name = "module_order")
    val order: Int,
)
