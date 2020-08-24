package com.example.academy.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CourseWithModule (
    @Embedded
    var mCourse: CourseEntity,

    @Relation(parentColumn = "courseId", entityColumn = "moduleId")
    var mModule: List<ModuleEntity>
)

