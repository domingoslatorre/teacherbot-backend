package com.domingoslatorre.teacherbot.teacherbotbackend.integration.course

import com.domingoslatorre.teacherbot.teacherbotbackend.common.ProblemDetail
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.CourseDto
import com.domingoslatorre.teacherbot.teacherbotbackend.course.service.dto.ModuleDto
import com.domingoslatorre.teacherbot.teacherbotbackend.util.TestPage
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity

val coursePageResParam = object : ParameterizedTypeReference<ResponseEntity<TestPage<CourseDto>>>() {}
val courseResParam = object : ParameterizedTypeReference<ResponseEntity<CourseDto>>() {}
val courseParam = object : ParameterizedTypeReference<CourseDto>() {}
val problemDetailResParam = object : ParameterizedTypeReference<ResponseEntity<ProblemDetail>>() {}
val problemDetailParam = object : ParameterizedTypeReference<ProblemDetail>() {}

val moduleResParam = object : ParameterizedTypeReference<ResponseEntity<ModuleDto>>() {}
val moduleListResParam = object : ParameterizedTypeReference<ResponseEntity<List<ModuleDto>>>() {}
val moduleParam = object : ParameterizedTypeReference<ModuleDto>() {}
