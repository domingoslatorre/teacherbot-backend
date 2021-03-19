package com.domingoslatorre.teacherbot.teacherbotbackend.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class Violation(val name: String, val reason: String)
data class ProblemDetail(val title: String, val violations: List<Violation>)

class AlreadyExistsException : RuntimeException()
class NotFoundException : RuntimeException()

@Suppress("unused")
@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [AlreadyExistsException::class])
    fun handleAlreadyExistsException(exception: AlreadyExistsException) =
        ResponseEntity(ProblemDetail("Resource already exists", listOf()), HttpStatus.CONFLICT)

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundError(exception: NotFoundException) =
        ResponseEntity(ProblemDetail("Resource not found", listOf()), HttpStatus.NOT_FOUND)

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
