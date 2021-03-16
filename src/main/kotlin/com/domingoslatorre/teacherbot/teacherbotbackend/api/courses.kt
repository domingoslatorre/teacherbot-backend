package com.domingoslatorre.teacherbot.teacherbotbackend.api

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.http.*
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
    fun existsByNameOrAcronym(name: String, acronym: String): Boolean

    @Query("select count(c) > 0 from Course c where (c.name = ?1 or c.acronym = ?2) and c.id != ?3")
    fun existsByNameOrAcronymExcludedId(name: String, acronym: String, id: UUID): Boolean
}

@RestController
@RequestMapping("courses")
class CourseController(val repo: CourseRepository) {
    private fun courseAlreadyExists(body: CourseReq) = repo.existsByNameOrAcronym(body.name!!, body.acronym!!)

    private fun courseAlreadyExistsExcludedId(body: CourseReq, id: UUID) =
        repo.existsByNameOrAcronymExcludedId(body.name!!, body.acronym!!, id)

    @GetMapping
    fun list(pageable: Pageable) = ResponseEntity.ok(repo.findAll(pageable).map { it.asResp() })

    @GetMapping("{id}")
    fun show(@PathVariable id: UUID) = ResponseEntity.ok(repo.findById(id).orElseThrow { NotFoundException() }.asResp())

    @PostMapping
    fun create(@Valid @RequestBody body: CourseReq) =
        if(courseAlreadyExists(body)) throw AlreadyExistsException()
        else ResponseEntity.created(URI.create("")).body(repo.save(body.asCourse()).asResp())

    @PutMapping("{id}")
    fun update(@Valid @RequestBody body: CourseReq, @PathVariable id: UUID) =
        repo.findById(id).orElseThrow { throw NotFoundException() }.let {
            if(courseAlreadyExistsExcludedId(body, id)) throw AlreadyExistsException()
            else ResponseEntity.ok(repo.save(Course(it.id, body.name!!, body.acronym!!, body.description!!)))
        }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) =
        repo.findById(id).orElseThrow { throw NotFoundException() }.let {
            repo.deleteById(id)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
}

data class CourseReq(
    @field:NotNull @field:NotBlank val name: String?,
    @field:NotNull @field:NotBlank @field:Size(min = 2, max = 5) val acronym: String?,
    @field:NotNull @field:NotBlank val description: String?,
)

fun CourseReq.asCourse() = Course(name = name!!, acronym = acronym!!, description = description!!)

data class CourseRes(
    val id: UUID,
    val name: String,
    val acronym: String,
    val description: String
)

fun Course.asResp() = CourseRes(id, name, acronym, description)