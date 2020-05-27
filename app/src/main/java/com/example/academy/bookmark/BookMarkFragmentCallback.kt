package com.example.academy.bookmark

import com.example.academy.data.CourseEntity

interface BookMarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
