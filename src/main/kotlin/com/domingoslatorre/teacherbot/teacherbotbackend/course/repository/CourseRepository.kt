package com.domingoslatorre.teacherbot.teacherbotbackend.course.repository

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CourseRepository : PagingAndSortingRepository<Course, UUID> {
    fun existsByNameOrAcronym(name: String, acronym: String): Boolean

    @Query("select count(c) > 0 from Course c where (c.name = ?1 or c.acronym = ?2) and c.id != ?3")
    fun existsByNameOrAcronymExcludedId(name: String, acronym: String, id: UUID): Boolean
}
