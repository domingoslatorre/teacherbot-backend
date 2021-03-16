package com.domingoslatorre.teacherbot.teacherbotbackend.requests

import com.domingoslatorre.teacherbot.teacherbotbackend.api.CourseReq
import io.kotest.core.spec.style.WordSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.*
import io.kotest.matchers.shouldBe
import javax.validation.*

class CourseCreateTeste: WordSpec() {
    init {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator;

        "CourseCreate" When {
            "name is valid" should {
                val violations = validator.validate(CourseReq("Lógica 2", "LG2", "Lógica de Prog..."))
                "have no violation" {
                    violations.shouldBeEmpty()
                }
            }
            "name is blank" should {
                val violations = validator.validate(CourseReq("  ", "LG2", "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "name"
                        it.message shouldBe "must not be blank"
                    }
                }
            }
            "name is null" should {
                val violations = validator.validate(CourseReq(null, "LG2", "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have null violation" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "name"
                        it.message shouldBe "must not be null"
                    }
                }
            }

            "acronym is blank" should {
                val violations = validator.validate(CourseReq("Lógica 2", "  ", "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "acronym"
                        it.message shouldBe "must not be blank"
                    }
                }
            }
            "acronym is null" should {
                val violations = validator.validate(CourseReq("Lógica 2", null, "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "acronym"
                        it.message shouldBe "must not be null"
                    }
                }
            }
            "acronym size is less than 2" should {
                val violations = validator.validate(CourseReq("Lógica 2", "L", "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "acronym"
                        it.message shouldBe "size must be between 2 and 5"
                    }
                }
            }
            "acronym size is greater than 5" should {
                val violations = validator.validate(CourseReq("Lógica 2", "LOGICA", "Lógica de Prog..."))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "acronym"
                        it.message shouldBe "size must be between 2 and 5"
                    }
                }
            }

            "description is blank" should {
                val violations = validator.validate(CourseReq("Lógica de Programação 2", "LG2", " "))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "description"
                        it.message shouldBe "must not be blank"
                    }
                }
            }
            "description is null" should {
                val violations = validator.validate(CourseReq("Lógica de Programação 2", "LG2", null))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have null violation" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "description"
                        it.message shouldBe "must not be null"
                    }
                }
            }



        }
    }
}