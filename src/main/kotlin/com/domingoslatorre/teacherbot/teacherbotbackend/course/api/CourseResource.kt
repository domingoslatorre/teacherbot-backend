package com.domingoslatorre.teacherbot.teacherbotbackend.course.api

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.CourseService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID
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
        .let { ResponseEntity.ok(it) }

    @PostMapping
    fun create(@Valid @RequestBody body: CourseReq) = service.create(body.name!!, body.description!!, body.acronym!!)
        .getOrThrow()
        .let { ResponseEntity.created(URI.create("/courses/${it.id}")).body(it) }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseReq, @PathVariable id: UUID) =
        service.update(id, body.name!!, body.description!!, body.acronym!!)
            .getOrThrow()
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> = service.delete(id)
        .getOrThrow()
        .let { ResponseEntity<Void>(HttpStatus.NO_CONTENT) }
}
