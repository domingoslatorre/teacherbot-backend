package com.domingoslatorre.teacherbot.teacherbotbackend.course.api

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.ModuleReq
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.ModuleService
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.asDto
import java.net.URI
import java.util.UUID
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("courses/{courseId}/modules")
class ModuleResource(val service: ModuleService) {

    @GetMapping
    fun list(@PathVariable courseId: UUID) =
        service.findAll(courseId)
            .getOrThrow()
            .let { ResponseEntity.ok(it) }

    @GetMapping("{moduleId}")
    fun show(@PathVariable courseId: UUID, @PathVariable moduleId: UUID) =
        service.findById(courseId, moduleId)
            .getOrThrow()
            .let { ResponseEntity.ok(it) }

    @PostMapping
    fun create(@PathVariable courseId: UUID, @Valid @RequestBody body: ModuleReq) =
        service.create(courseId, body.title!!, body.objective!!, body.position!!)
            .getOrThrow()
            .let { ResponseEntity.created(URI.create("courses/$courseId/modules/${it.id}")).body(it.asDto()) }
}
