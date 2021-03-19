package com.domingoslatorre.teacherbot.teacherbotbackend

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("health")
class HealthCheck {

    @GetMapping
    fun health() = ResponseEntity.ok("Ok")
}
