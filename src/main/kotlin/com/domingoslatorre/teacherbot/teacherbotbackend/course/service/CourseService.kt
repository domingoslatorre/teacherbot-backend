package com.domingoslatorre.teacherbot.teacherbotbackend.course.service

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Course
import com.domingoslatorre.teacherbot.teacherbotbackend.course.repository.CourseRepository
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.CourseDto
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.asDto
import java.lang.RuntimeException
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

class CourseAlreadyExistsException(val name: String, val acronym: String) : RuntimeException()
class CourseNotFoundException(val id: UUID) : RuntimeException()

@Service
@Transactional
class CourseService(val repo: CourseRepository) {

    fun create(name: String, description: String, acronym: String): Result<CourseDto> =
        checkCourseNotExists(repo, name, acronym).fold(
            { Result.success(repo.save(Course(name = name, description = description, acronym = acronym)).asDto()) },
            { Result.failure(it) }
        )

    fun update(id: UUID, name: String, description: String, acronym: String): Result<CourseDto> =
        findCourseById(repo, id).fold(
            {
                if (repo.existsByNameOrAcronymExcludedId(name, acronym, id)) {
                    Result.failure(CourseAlreadyExistsException(name, acronym))
                } else Result.success(repo.save(it.update(name, description, acronym)).asDto())
            },
            { Result.failure(it) }
        )

    fun delete(id: UUID): Result<Unit> = findCourseById(repo, id)
        .fold(
            { Result.success(repo.delete(it)) },
            { Result.failure(it) }
        )

    fun findById(id: UUID): Result<CourseDto> = findCourseById(repo, id)
        .fold(
            { Result.success(it.asDto()) },
            { Result.failure(it) }
        )

    fun findAll(pageable: Pageable): Result<Page<CourseDto>> =
        Result.success(repo.findAll(pageable).map { it.asDto() })
}

fun findCourseById(repo: CourseRepository, id: UUID): Result<Course> = repo.findById(id).let { opt ->
    if (opt.isPresent) Result.success(opt.get())
    else Result.failure(CourseNotFoundException(id))
}

fun checkCourseNotExists(repo: CourseRepository, name: String, acronym: String): Result<Boolean> =
    if (repo.existsByNameOrAcronym(name, acronym)) Result.failure(CourseAlreadyExistsException(name, acronym))
    else Result.success(true)
