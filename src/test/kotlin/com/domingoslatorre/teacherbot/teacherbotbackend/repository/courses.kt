package com.domingoslatorre.teacherbot.teacherbotbackend.repository

import com.domingoslatorre.teacherbot.teacherbotbackend.api.*
import io.kotest.matchers.booleans.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class CourseRepositoryTeste(
    @Autowired val courseRepository: CourseRepository
) {

    @Test
    fun `should exists by name or acronym`() {
        val course1 = Course(name = "Lógica 1", acronym = "LG1", description = "...").also { courseRepository.save(it) }
        val course2 = Course(name = "Lógica 2", acronym = "LG2", description = "...").also { courseRepository.save(it) }

        courseRepository.existsByNameOrAcronym(course1.name, "ABC").shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course2.name, "ABC").shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", course2.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course1.name, course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course2.name, course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", "LP1").shouldBeFalse()
    }

    @Test
    fun `should find a course by name`() {
        val course1 = Course(name = "Lógica 1", acronym = "LG1", description = "...").also { courseRepository.save(it) }
        val course2 = Course(name = "Lógica 2", acronym = "LG2", description = "...").also { courseRepository.save(it) }

        courseRepository.findByName(course1.name).get().apply {
            id shouldBe course1.id
            name shouldBe course1.name
            acronym shouldBe course1.acronym
            description shouldBe course1.description
        }

        courseRepository.findByName(course2.name).get().apply {
            id shouldBe course2.id
            name shouldBe course2.name
            acronym shouldBe course2.acronym
            description shouldBe course2.description
        }

        courseRepository.findByName("Linguagem 1").isPresent.shouldBeFalse()

    }

    @Test
    fun `should find a course by acronym`() {
        val course1 = Course(name = "Lógica 1", acronym = "LG1", description = "...").also { courseRepository.save(it) }
        val course2 = Course(name = "Lógica 2", acronym = "LG2", description = "...").also { courseRepository.save(it) }

        courseRepository.findByAcronym(course1.acronym).get().apply {
            id shouldBe course1.id
            name shouldBe course1.name
            acronym shouldBe course1.acronym
            description shouldBe course1.description
        }

        courseRepository.findByAcronym(course2.acronym).get().apply {
            id shouldBe course2.id
            name shouldBe course2.name
            acronym shouldBe course2.acronym
            description shouldBe course2.description
        }

        courseRepository.findByAcronym("LP1").isPresent.shouldBeFalse()

    }
}