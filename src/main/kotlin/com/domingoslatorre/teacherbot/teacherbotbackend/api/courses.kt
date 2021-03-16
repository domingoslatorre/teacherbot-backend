package com.domingoslatorre.teacherbot.teacherbotbackend.api

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.*

@Entity
class Course(
    @Id val id: UUID = UUID.randomUUID(),
    val name: String,
    val acronym: String,
    val description: String,
)

@Repository
interface CourseRepository : PagingAndSortingRepository<Course, UUID> {
    fun existsByName(name: String): Boolean
}

@RestController
@RequestMapping("courses")
class CourseController(val repo: CourseRepository) {
    @GetMapping
    fun list(pageable: Pageable) = ResponseEntity.ok(repo.findAll(pageable).map { it.asResp() })

    @GetMapping("{id}")
    fun show(@PathVariable id: UUID) = ResponseEntity.ok(repo.findById(id).orElseThrow { NotFoundException() }.asResp())

    @PostMapping
    fun create(@Valid @RequestBody body: CourseCreate) =
        if(repo.existsByName(body.name!!)) throw AlreadyExistsException()
        else ResponseEntity.created(URI.create("")).body(repo.save(body.asCourse()).asResp())

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseUpdate, @PathVariable id: UUID) =
        repo.findById(id).orElseThrow { throw NotFoundException() }.let {
            if(repo.existsByName(body.name!!)) throw AlreadyExistsException()
            else ResponseEntity.ok(repo.save(Course(it.id, body.name!!, body.acronym!!, body.description!!)))
        }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) =
        repo.findById(id).orElseThrow { throw NotFoundException() }.let {
            repo.deleteById(id)
            ResponseEntity.noContent()
        }
}

data class CourseCreate(
    @field:NotNull @field:NotBlank val name: String?,
    @field:NotNull @field:NotBlank val acronym: String?,
    @field:NotNull @field:NotBlank val description: String?,
)

fun CourseCreate.asCourse() = Course(name = name!!, acronym = acronym!!, description = description!!)

data class CourseUpdate(
    @field:NotNull @field:NotBlank val name: String?,
    @field:NotNull @field:NotBlank val acronym: String?,
    @field:NotNull @field:NotBlank val description: String?,
)

data class CourseResponse(
    val id: UUID,
    val name: String,
    val acronym: String,
    val description: String
)

fun Course.asResp() = CourseResponse(id, name, acronym, description)