package com.example.academy.ui.bookmark

import com.example.academy.data.source.local.entity.CourseEntity

interface BookMarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
