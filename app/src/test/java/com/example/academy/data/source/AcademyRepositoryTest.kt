package com.example.academy.data.source

import android.provider.ContactsContract
import com.example.academy.data.source.remote.RemoteDataSource
import com.example.academy.data.source.remote.response.ContentResponse
import com.example.academy.data.source.remote.response.CourseResponse
import com.example.academy.data.source.remote.response.ModuleResponse
import com.example.academy.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito

class AcademyRepositoryTest {

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourse()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses() {
        Mockito.`when`<List<CourseResponse>>(remote.getAllCourse()).thenReturn(courseResponses)
        val courseEntities = academyRepository.getAllCourses()
        Mockito.verify<RemoteDataSource>(remote).getAllCourse()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getAllModulesByCourse() {
        Mockito.`when`<List<ModuleResponse>>(remote.getModules(courseId)).thenReturn(moduleResponses)
        val moduleEntities = academyRepository.getAllModulesByCourse(courseId)
        Mockito.verify<RemoteDataSource>(remote).getModules(courseId)
        assertNotNull(moduleEntities)
        assertEquals(moduleResponses.size.toLong(), moduleEntities.size.toLong())
    }

    @Test
    fun getBookMarkedCourses() {
        Mockito.`when`<List<CourseResponse>>(remote.getAllCourse()).thenReturn(courseResponses)
        val courseEntities = academyRepository.getBookmarkedCourses()
        Mockito.verify<RemoteDataSource>(remote).getAllCourse()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getContent() {
        Mockito.`when`<List<ModuleResponse>>(remote.getModules(courseId)).thenReturn(moduleResponses)
        Mockito.`when`<ContentResponse>(remote.getContent(moduleId)).thenReturn(content)
        val resultModule = academyRepository.getContent(courseId, moduleId)
        Mockito.verify<RemoteDataSource>(remote).getContent(moduleId)
        assertNotNull(resultModule)
        assertEquals(content.content, resultModule.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        Mockito.`when`<List<CourseResponse>>(remote.getAllCourse()).thenReturn(courseResponses)
        val resultCourse = academyRepository.getCourseWiithModules(courseId)
        Mockito.verify<RemoteDataSource>(remote).getAllCourse()
        assertNotNull(resultCourse)
        assertEquals(courseResponses[0].title, resultCourse.title)
    }
}