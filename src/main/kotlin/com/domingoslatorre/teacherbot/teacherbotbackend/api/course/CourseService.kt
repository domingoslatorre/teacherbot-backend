package com.domingoslatorre.teacherbot.teacherbotbackend.api.course

import com.domingoslatorre.teacherbot.teacherbotbackend.common.*
import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.model.*
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(val repo: CourseRepository) {

    fun create(name: String, description: String, acronym: String): Course =
        if(repo.existsByNameOrAcronym(name, acronym)) throw AlreadyExistsException()
        else repo.save(Course(name = name, description = description, acronym = acronym))

    fun update(id: UUID, name: String, description: String, acronym: String): Course =
        repo.findById(id).orElseThrow { NotFoundException() }.let {
            if(repo.existsByNameOrAcronymExcludedId(name, acronym, id)) throw AlreadyExistsException()
            else repo.save(it.update(name, description, acronym))
        }

    fun findById(id: UUID): Course = repo.findById(id).orElseThrow { NotFoundException() }

    fun findAll(pageable: Pageable): Page<Course> = repo.findAll(pageable)

    fun delete(id: UUID) { repo.findById(id).orElseThrow { NotFoundException() }.let { repo.delete(it) } }

    fun createModule(courseId: UUID, title: String, order: Int): Course =
        repo.findById(courseId).orElseThrow { NotFoundException() }.let { course ->
            try {
                course.addModule(title, order)
                repo.save(course)
            } catch (ex: ModuleAlreadyExists) {
                throw AlreadyExistsException()
            }
        }

}