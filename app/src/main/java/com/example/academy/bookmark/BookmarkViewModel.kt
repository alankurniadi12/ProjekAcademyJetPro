package com.example.academy.bookmark

import androidx.lifecycle.ViewModel
import com.example.academy.data.CourseEntity
import com.example.academy.utils.DataDummy

class BookmarkViewModel: ViewModel() {

    fun getBookmark(): List<CourseEntity> = DataDummy.generateDummyCourses()
}