package com.domingoslatorre.teacherbot.teacherbotbackend.api.course

import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.dto.*
import org.springframework.data.domain.Pageable
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("courses")
class CourseController(val service: CourseService) {

    @GetMapping
    fun list(pageable: Pageable) = ResponseEntity.ok(service.findAll(pageable).map { it.asResp() })

    @GetMapping("{id}")
    fun show(@PathVariable id: UUID) = ResponseEntity.ok(service.findById(id).asResp())

    @PostMapping
    fun create(@Valid @RequestBody body: CourseReq) =
        service.create(body.name!!, body.description!!, body.acronym!!).let {
            ResponseEntity.created(URI.create("")).body(it.asResp())
        }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseReq, @PathVariable id: UUID) =
        service.update(id, body.name!!, body.description!!, body.acronym!!).let { ResponseEntity.ok(it.asResp()) }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

    @PostMapping("{courseId}/modules")
    fun createModule(@Valid @RequestBody body: ModuleReq, @PathVariable courseId: UUID) =
        service.createModule(courseId, body.title!!, body.order!!).let {
            ResponseEntity.created(URI.create("")).body(it.asResp())
        }
}

