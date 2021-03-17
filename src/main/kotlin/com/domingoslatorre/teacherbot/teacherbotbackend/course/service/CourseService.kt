package com.domingoslatorre.teacherbot.teacherbotbackend.course.service

import com.domingoslatorre.teacherbot.teacherbotbackend.course.repository.CourseRepository
import com.domingoslatorre.teacherbot.teacherbotbackend.common.*
import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.*
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.*
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(val repo: CourseRepository) {

    fun create(name: String, description: String, acronym: String): CourseDto =
        if(repo.existsByNameOrAcronym(name, acronym)) throw AlreadyExistsException()
        else repo.save(Course(name = name, description = description, acronym = acronym)).asDto()

    fun update(id: UUID, name: String, description: String, acronym: String): CourseDto =
        repo.findById(id).orElseThrow { NotFoundException() }.let {
            if(repo.existsByNameOrAcronymExcludedId(name, acronym, id)) throw AlreadyExistsException()
            else repo.save(it.update(name, description, acronym)).asDto()
        }

    fun findById(id: UUID): CourseDto = repo.findById(id).orElseThrow { NotFoundException() }.asDto()

    fun findAll(pageable: Pageable): Page<CourseDto> = repo.findAll(pageable).map { it.asDto() }

    fun delete(id: UUID) { repo.findById(id).orElseThrow { NotFoundException() }.let { repo.delete(it) } }
}