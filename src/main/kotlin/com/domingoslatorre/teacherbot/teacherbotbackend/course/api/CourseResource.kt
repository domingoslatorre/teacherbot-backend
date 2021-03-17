package com.domingoslatorre.teacherbot.teacherbotbackend.course.api

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.*
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.CourseService
import org.springframework.data.domain.Pageable
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("courses")
class CourseResource(val service: CourseService) {

    @GetMapping
    fun list(pageable: Pageable) = ResponseEntity.ok(service.findAll(pageable).map { it })

    @GetMapping("{id}")
    fun show(@PathVariable id: UUID) = ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun create(@Valid @RequestBody body: CourseReq) =
        service.create(body.name!!, body.description!!, body.acronym!!).let {
            ResponseEntity.created(URI.create("")).body(it)
        }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseReq, @PathVariable id: UUID) =
        service.update(id, body.name!!, body.description!!, body.acronym!!).let { ResponseEntity.ok(it) }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }
}

