package com.domingoslatorre.teacherbot.teacherbotbackend.course.model

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Module(
    @Id
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val objective: String,
    val position: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Module

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}
