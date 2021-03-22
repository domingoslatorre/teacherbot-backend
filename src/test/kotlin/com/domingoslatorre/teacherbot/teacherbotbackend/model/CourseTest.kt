package com.domingoslatorre.teacherbot.teacherbotbackend.model

import com.domingoslatorre.teacherbot.teacherbotbackend.course.model.ModuleAlreadyExistsException
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.CourseFactory
import com.domingoslatorre.teacherbot.teacherbotbackend.factory.ModuleFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CourseTest : FunSpec({
    context("Create a course") {
        val course = CourseFactory.course()

        test("should has a empty set of modules") {
            course.modules.shouldBeEmpty()
            course.modules.shouldBeInstanceOf<MutableSet<Module>>()
        }
    }

    context("Create a course and add modules") {
        val course = CourseFactory.course()

        val module1 = ModuleFactory.module(title = "Module 1")
        course.addModule(module1.title, module1.objective, module1.position)

        val module2 = ModuleFactory.module(title = "Module 2")
        course.addModule(module2.title, module2.objective, module2.position)

        test("should have two modules") {
            course.modules shouldHaveSize 2
        }

        test("should have module1 and module2 data") {
            course.modules.forAtLeastOne {
                it.title shouldBe module1.title
                it.objective shouldBe module1.objective
                it.position shouldBe module1.position
            }

            course.modules.forAtLeastOne {
                it.title shouldBe module2.title
                it.objective shouldBe module2.objective
                it.position shouldBe module2.position
            }
        }
    }

    context("Create a course and add modules with same title") {
        val course = CourseFactory.course()
        val title = "Module 1"

        val module1 = ModuleFactory.module(title = title)
        course.addModule(module1.title, module1.objective, module1.position)

        val module2 = ModuleFactory.module(title = title)
        val result = course.addModule(module2.title, module2.objective, module2.position)

        test("should have one modules") {
            course.modules shouldHaveSize 1
        }

        test("should return a result of failure") {
            result.isFailure.shouldBeTrue()
        }

        test("should return a ModuleAlreadyExistsException") {
            result.exceptionOrNull()!!.shouldBeInstanceOf<ModuleAlreadyExistsException>()
        }

        test("should return course id and title inside the exception") {
            (result.exceptionOrNull()!! as ModuleAlreadyExistsException).apply {
                courseId shouldBe course.id
                title shouldBe title
            }
        }
    }

    context("Create a course, add modules and update") {
        val course = CourseFactory.course()

        val module1 = ModuleFactory.module(title = "Module 1")
        course.addModule(module1.title, module1.objective, module1.position)

        val module2 = ModuleFactory.module(title = "Module 2")
        val module2Add = course.addModule(module2.title, module2.objective, module2.position).getOrNull()!!

        test("should have two modules before update") {
            course.modules shouldHaveSize 2
        }

        test("should have module1 and module2 data") {
            course.modules.forAtLeastOne {
                it.title shouldBe module1.title
                it.objective shouldBe module1.objective
                it.position shouldBe module1.position
            }

            course.modules.forAtLeastOne {
                it.title shouldBe module2.title
                it.objective shouldBe module2.objective
                it.position shouldBe module2.position
            }
        }

        val updatedModule = ModuleFactory.module(
            id = module2Add.id,
            title = "Module 3",
            objective = "Module 3 objective",
            position = 3
        )

        course.updateModule(updatedModule.id, updatedModule.title, updatedModule.objective, updatedModule.position)

        test("should have two modules after update") {
            course.modules shouldHaveSize 2
        }

        test("should have update module data") {
            course.modules.forAtLeastOne {
                it.title shouldBe module1.title
                it.objective shouldBe module1.objective
                it.position shouldBe module1.position
            }

            course.modules.forAtLeastOne {
                it.title shouldBe updatedModule.title
                it.objective shouldBe updatedModule.objective
                it.position shouldBe updatedModule.position
            }
        }
    }

    context("Create a course, add modules and update with same title") {
        val course = CourseFactory.course()

        val module1 = ModuleFactory.module(title = "Module 1")
        course.addModule(module1.title, module1.objective, module1.position)

        val module2 = ModuleFactory.module(title = "Module 2")
        val module2Add = course.addModule(module2.title, module2.objective, module2.position).getOrNull()!!

        test("should have two modules before update") {
            course.modules shouldHaveSize 2
        }

        test("should have module1 and module2 data") {
            course.modules.forAtLeastOne {
                it.title shouldBe module1.title
                it.objective shouldBe module1.objective
                it.position shouldBe module1.position
            }

            course.modules.forAtLeastOne {
                it.title shouldBe module2.title
                it.objective shouldBe module2.objective
                it.position shouldBe module2.position
            }
        }

        val updatedModule = ModuleFactory.module(
            id = module2Add.id,
            title = module1.title,
            objective = "Module 3 objective",
            position = 3
        )

        val result =
            course.updateModule(updatedModule.id, updatedModule.title, updatedModule.objective, updatedModule.position)

        test("should return a result of failure") {
            result.isFailure.shouldBeTrue()
        }

        test("should return a ModuleAlreadyExistsException") {
            result.exceptionOrNull()!!.shouldBeInstanceOf<ModuleAlreadyExistsException>()
        }

        test("should have two modules after update") {
            course.modules shouldHaveSize 2
        }

        test("should not update module1 and module2 data") {
            course.modules.forAtLeastOne {
                it.title shouldBe module1.title
                it.objective shouldBe module1.objective
                it.position shouldBe module1.position
            }

            course.modules.forAtLeastOne {
                it.title shouldBe module2.title
                it.objective shouldBe module2.objective
                it.position shouldBe module2.position
            }
        }
    }
})
