package com.domingoslatorre.teacherbot.teacherbotbackend.course.service

import com.domingoslatorre.teacherbot.teacherbotbackend.common.AlreadyExistsException
import com.domingoslatorre.teacherbot.teacherbotbackend.common.NotFoundException
import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.Course
import com.domingoslatorre.teacherbot.teacherbotbackend.course.repository.CourseRepository
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.CourseDto
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.asDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CourseService(val repo: CourseRepository) {

    fun create(name: String, description: String, acronym: String): Result<CourseDto> = courseNotExists(name, acronym)
        .fold(
            { Result.success(repo.save(Course(name = name, description = description, acronym = acronym)).asDto()) },
            { Result.failure(AlreadyExistsException()) }
        )

    fun update(id: UUID, name: String, description: String, acronym: String): Result<CourseDto> = findByIdRepo(id)
        .fold(
            {
                if (repo.existsByNameOrAcronymExcludedId(name, acronym, id)) Result.failure(AlreadyExistsException())
                else Result.success(repo.save(it.update(name, description, acronym)).asDto())
            },
            { Result.failure(it) }
        )

    fun delete(id: UUID): Result<Unit> = findByIdRepo(id)
        .fold(
            { Result.success(repo.delete(it)) },
            { Result.failure(it) }
        )

    fun findById(id: UUID): Result<CourseDto> = findByIdRepo(id)
        .fold(
            { Result.success(it.asDto()) },
            { Result.failure(it) }
        )

    fun findAll(pageable: Pageable): Result<Page<CourseDto>> =
        Result.success(repo.findAll(pageable).map { it.asDto() })

    private fun findByIdRepo(id: UUID): Result<Course> = repo.findById(id).let { opt ->
        if (opt.isPresent) Result.success(opt.get())
        else Result.failure(NotFoundException())
    }

    private fun courseNotExists(name: String, acronym: String): Result<Boolean> =
        if (repo.existsByNameOrAcronym(name, acronym)) Result.failure(AlreadyExistsException())
        else Result.success(true)
}
