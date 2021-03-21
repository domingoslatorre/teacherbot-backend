package com.domingoslatorre.teacherbot.teacherbotbackend.requests

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.ModuleReqFactory
import io.kotest.core.spec.style.WordSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe
import javax.validation.Validation
import javax.validation.Validator

@Suppress("unused")
class ModuleReqTest : WordSpec() {
    init {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator

        "ModuleCreate" When {
            "title, objective and position is valid" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq())
                "have no violation" {
                    violations.shouldBeEmpty()
                }
            }
            "title is blank" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(title = ""))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "title"
                        it.message shouldBe "must not be blank"
                    }
                }
            }
            "title is null" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(title = null))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have null violation" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "title"
                        it.message shouldBe "must not be null"
                    }
                }
            }
            "objective is blank" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(objective = ""))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "objective"
                        it.message shouldBe "must not be blank"
                    }
                }
            }
            "objective is null" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(objective = null))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "objective"
                        it.message shouldBe "must not be null"
                    }
                }
            }
            "position is less than 1" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(position = 0))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have blank violation object" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "position"
                        it.message shouldBe "must be greater than or equal to 1"
                    }
                }
            }
            "position is null" should {
                val violations = validator.validate(ModuleReqFactory.moduleReq(position = null))
                "have 1 violation" {
                    violations.shouldHaveAtLeastSize(1)
                }
                "have null violation" {
                    violations.forExactly(1) {
                        it.propertyPath.toString() shouldBe "position"
                        it.message shouldBe "must not be null"
                    }
                }
            }
        }
    }
}
