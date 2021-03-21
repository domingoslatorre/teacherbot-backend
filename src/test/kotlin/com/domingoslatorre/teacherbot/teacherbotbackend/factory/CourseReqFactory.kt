package com.domingoslatorre.teacherbot.teacherbotbackend.factory

import com.domingoslatorre.teacherbot.teacherbotbackend.course.api.requests.CourseReq

object CourseReqFactory {
    fun courseReq(
        name: String = "Course 1",
        acronym: String = "CO1",
        description: String = "Course description ...",
    ) = CourseReq(name, acronym, description)

    fun courseReq2() = this.courseReq(name = "Course 2", acronym = "CO2")
    fun courseReq3() = this.courseReq(name = "Course 3", acronym = "CO3")
}
