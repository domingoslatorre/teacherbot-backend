package com.domingoslatorre.teacherbot.teacherbotbackend.repository

import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.*
import com.domingoslatorre.teacherbot.teacherbotbackend.api.course.model.Course
import io.kotest.matchers.booleans.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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
        val course1 = Course(name = "Lógica 1", acronym = "LG1", description = "...", modules = mutableListOf()).also {
            courseRepository.save(it)
        }
        val course2 = Course(name = "Lógica 2", acronym = "LG2", description = "...", modules = mutableListOf()).also {
            courseRepository.save(it)
        }

        courseRepository.existsByNameOrAcronym(course1.name, "ABC").shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course2.name, "ABC").shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", course2.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course1.name, course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym(course2.name, course1.acronym).shouldBeTrue()
        courseRepository.existsByNameOrAcronym("Linguagem 1", "LP1").shouldBeFalse()
    }

    @Test
    fun `should exists by name or acronym, excluded id`() {
        val course1 = Course(name = "Lógica 1", acronym = "LG1", description = "...", modules = mutableListOf()).also {
            courseRepository.save(it)
        }
        val course2 = Course(name = "Lógica 2", acronym = "LG2", description = "...", modules = mutableListOf()).also {
            courseRepository.save(it)
        }

        courseRepository.existsByNameOrAcronymExcludedId(course1.name, course1.acronym, course2.id).shouldBeTrue()
        courseRepository.existsByNameOrAcronymExcludedId(course1.name, "ABC", course2.id).shouldBeTrue()
        courseRepository.existsByNameOrAcronymExcludedId("Linguagem", "ABC", course2.id).shouldBeFalse()
        courseRepository.existsByNameOrAcronymExcludedId("Lógica 2", "LG2", course2.id).shouldBeFalse()
        courseRepository.existsByNameOrAcronymExcludedId("Lógica 2", "LG3", course2.id).shouldBeFalse()
        courseRepository.existsByNameOrAcronymExcludedId("Lógica 3", "LG2", course2.id).shouldBeFalse()
    }
}