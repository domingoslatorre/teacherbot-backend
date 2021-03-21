package com.domingoslatorre.teacherbot.teacherbotbackend.common

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.ModuleAlreadyExistsException
import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.ModuleNotFoundException
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.CourseAlreadyExistsException
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.CourseNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class Violation(val name: String, val reason: String)
data class ProblemDetail(val title: String, val violations: List<Violation> = listOf())

@Suppress("unused")
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [CourseAlreadyExistsException::class])
    fun handleCourseAlreadyExistsException(exception: CourseAlreadyExistsException) =
        ResponseEntity(
            ProblemDetail("Course already exists with name ${exception.name} and acronym ${exception.acronym}"),
            HttpStatus.CONFLICT
        )

    @ExceptionHandler(value = [CourseNotFoundException::class])
    fun handleCourseNotFoundException(exception: CourseNotFoundException) =
        ResponseEntity(ProblemDetail("Course not found with id ${exception.id}"), HttpStatus.NOT_FOUND)

    @ExceptionHandler(value = [ModuleAlreadyExistsException::class])
    fun handleModuleAlreadyExistsException(exception: ModuleAlreadyExistsException) =
        ResponseEntity(
            ProblemDetail("Module already exists with title ${exception.title} course id ${exception.courseId}"),
            HttpStatus.CONFLICT
        )

    @ExceptionHandler(value = [ModuleNotFoundException::class])
    fun handleModuleNotFoundException(exception: ModuleNotFoundException) =
        ResponseEntity(
            ProblemDetail("Module not found with id ${exception.moduleId} and course id ${exception.courseId}"),
            HttpStatus.NOT_FOUND
        )

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) =
        ResponseEntity(
            ProblemDetail(
                "Your request parameters didn't validate",
                exception.fieldErrors.map { Violation(it.field, it.defaultMessage ?: "") }
            ),
            HttpStatus.BAD_REQUEST
        )
}
