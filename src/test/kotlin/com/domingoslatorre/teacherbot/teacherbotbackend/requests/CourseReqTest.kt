package com.domingoslatorre.teacherbot.teacherbotbackend.requests

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseReqFactory
import io.kotest.core.spec.style.WordSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe
import javax.validation.Validation
import javax.validation.Validator

@Suppress("unused")
class CourseReqTest : WordSpec() {
    init {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator

        "CourseCreate" When {
            "name is valid" should {
                val violations = validator.validate(CourseReqFactory.courseReq())
                "have no violation" {
                    violations.shouldBeEmpty()
                }
            }
            "name is blank" should {
                val violations = validator.validate(CourseReqFactory.courseReq(name = "  "))
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
                val violations = validator.validate(CourseReqFactory.courseReq(name = null))
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
                val violations = validator.validate(CourseReqFactory.courseReq(acronym = "  "))
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
                val violations = validator.validate(CourseReqFactory.courseReq(acronym = null))
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
                val violations = validator.validate(CourseReqFactory.courseReq(acronym = "C"))
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
                val violations = validator.validate(CourseReqFactory.courseReq(acronym = "COURSE"))
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
                val violations = validator.validate(CourseReqFactory.courseReq(description = "  "))
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
                val violations = validator.validate(CourseReqFactory.courseReq(description = null))
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
