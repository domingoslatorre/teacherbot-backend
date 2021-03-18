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
    fun list(pageable: Pageable) = service.findAll(pageable)
        .getOrThrow()
        .let { ResponseEntity.ok(it) }

    @GetMapping("{id}")
    fun show(@PathVariable id: UUID) = service.findById(id)
        .getOrThrow()
        .let { ResponseEntity.ok(it)  }

    @PostMapping
    fun create(@Valid @RequestBody body: CourseReq) = service.create(body.name!!, body.description!!, body.acronym!!)
        .getOrThrow()
        .let { ResponseEntity.created(URI.create("")).body(it) }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseReq, @PathVariable id: UUID) =
        service.update(id, body.name!!, body.description!!, body.acronym!!)
            .getOrThrow()
            .let { ResponseEntity.ok(it)  }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> = service.delete(id)
        .getOrThrow()
        .let { ResponseEntity<Void>(HttpStatus.NO_CONTENT) }
}

