package com.domingoslatorre.teacherbot.teacherbotbackend.course.service

import com.domingoslatorre.teacherbot.teacherbotbackend.course.repository.CourseRepository
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.asDto
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModuleService(val repo: CourseRepository) {

    fun findAll(courseId: UUID) = findCourseById(repo, courseId)
        .fold(
            { Result.success(it.modules.toList().map { module -> module.asDto() }) },
            { Result.failure(it) }
        )

    fun findById(courseId: UUID, moduleId: UUID) = findCourseById(repo, courseId)
        .fold(
            { course ->
                course.getModuleById(moduleId).fold(
                    { Result.success(it.asDto()) },
                    { Result.failure(it) }
                )
            },
            { Result.failure(it) }
        )

    fun create(courseId: UUID, title: String, objective: String, position: Int) = findCourseById(repo, courseId)
        .fold(
            { course ->
                course.addModule(title, objective, position).fold(
                    {
                        repo.save(course)
                        Result.success(it)
                    },
                    { Result.failure(it) }
                )
            },
            { Result.failure(it) }
        )

    fun update(courseId: UUID, moduleId: UUID, title: String, objective: String, position: Int) =
        findCourseById(repo, courseId)
            .fold(
                { course ->
                    course.addModule(title, objective, position).fold(
                        { repo.save(course).run { Result.success(it.asDto()) } },
                        { Result.failure(it) }
                    )
                },
                { Result.failure(it) }
            )
}
