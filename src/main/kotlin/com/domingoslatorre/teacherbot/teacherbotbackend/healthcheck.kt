package com.domingoslatorre.teacherbot.teacherbotbackend.api

import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("health")
class HealthCheck {

    @GetMapping()
    fun health() = ResponseEntity<Void>(HttpStatus.OK)
}