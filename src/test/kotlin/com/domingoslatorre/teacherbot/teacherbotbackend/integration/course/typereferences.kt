package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.api.*
import com.domingoslatorre.teacherbot.teacherbotbackend.util.TestPage
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity

val coursePageResParam = object : ParameterizedTypeReference<ResponseEntity<TestPage<CourseRes>>>() {}
val courseResParam = object : ParameterizedTypeReference<ResponseEntity<CourseRes>>() {}
val courseParam = object : ParameterizedTypeReference<CourseRes>() {}
val problemDetailResParam = object : ParameterizedTypeReference<ResponseEntity<ProblemDetail>>() {}
val problemDetailParam = object : ParameterizedTypeReference<ProblemDetail>() {}
