package com.domingoslatorre.teacherbot.teacherbotbackend

import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("health")
class HealthCheck {

    @GetMapping()
    fun health() = ResponseEntity.ok("Ok")
}