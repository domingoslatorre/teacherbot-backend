package com.domingoslatorre.teacherbot.teacherbotbackend.api

import org.springframework.http.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

data class Violation(val name: String, val reason: String)
data class ProblemDetail(val title: String, val violations: List<Violation>)

class AlreadyExistsException : RuntimeException()
class NotFoundException: RuntimeException()

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [AlreadyExistsException::class])
    fun handleAlreadyExistsException(exception: AlreadyExistsException) =
        ResponseEntity(ProblemDetail("Resource already exists", listOf()), HttpStatus.CONFLICT)

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundError(exception: NotFoundException) =
        ResponseEntity(ProblemDetail("Resource not found", listOf()), HttpStatus.CONFLICT)


    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) =
        ResponseEntity(
            ProblemDetail(
                "Your request parameters didn't validate",
                exception.fieldErrors.map { Violation(it.field, it.defaultMessage ?: "") }
            ),
            HttpStatus.BAD_REQUEST
        )

//    TODO: HttpRequestMethodNotSupportedException
}