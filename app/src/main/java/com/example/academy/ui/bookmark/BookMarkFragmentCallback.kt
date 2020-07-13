package com.example.academy.ui.bookmark

import com.example.academy.data.CourseEntity

interface BookMarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
