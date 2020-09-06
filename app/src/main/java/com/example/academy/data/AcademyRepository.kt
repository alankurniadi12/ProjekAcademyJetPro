package com.example.academy.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.academy.data.source.local.LocalDataSource
import com.example.academy.data.source.local.entity.CourseEntity
import com.example.academy.data.source.local.entity.CourseWithModule
import com.example.academy.data.source.local.entity.ModuleEntity
import com.example.academy.data.source.remote.ApiResponse
import com.example.academy.data.source.remote.RemoteDataSource
import com.example.academy.data.source.remote.response.ContentResponse
import com.example.academy.data.source.remote.response.CourseResponse
import com.example.academy.data.source.remote.response.ModuleResponse
import com.example.academy.vo.Resource
import com.example.academy.utils.AppExecutors

class AcademyRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDadaSource: LocalDataSource,
    private val appExecutors: AppExecutors
): AcademyDataSource {

    companion object {
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): AcademyRepository =
            instance ?: synchronized(this) {
                instance ?: AcademyRepository(remoteData, localData, appExecutors)
        }
    }

    override fun getAllCourses(): LiveData<Resource<List<CourseEntity>>> {
        return object : NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors) {

            public override fun loadFromDB(): LiveData<List<CourseEntity>> =
                localDadaSource.getAllCourse()

            override fun shouldFetch(data: List<CourseEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> =
                remoteDataSource.getAllCourse()

            public override fun saveCallResult(data: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in data) {
                    val course = CourseEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }
                localDadaSource.insertCourses(courseList)
            }
        }.asLiveData()
    }

    override fun getCourseWiithModules(courseId: String): LiveData<Resource<CourseWithModule>> {
        return object : NetworkBoundResource<CourseWithModule, List<ModuleResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<CourseWithModule> =
                localDadaSource.getCourseWithModules(courseId)

            override fun shouldFetch(data: CourseWithModule?): Boolean =
                data?.mModule == null || data.mModule.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()

                for(response in data) {
                    val course = ModuleEntity(
                        response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)
                    moduleList.add(course)
                }
                Log.e("AcademyRepository", "ModuleList: $moduleList")
                localDadaSource.insertModules(moduleList)
            }

        }.asLiveData()
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>> {
        return object : NetworkBoundResource<List<ModuleEntity>, List<ModuleResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<ModuleEntity>> =
                localDadaSource.getAllModulesByCourse(courseId)

            override fun shouldFetch(data: List<ModuleEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for(response in data) {
                    val course = ModuleEntity(
                        response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false
                    )
                    moduleList.add(course)
                }
                Log.e("AcademyRepository", "getAllModulesByCourese: $moduleList")
                localDadaSource.insertModules(moduleList)
            }

        }.asLiveData()

    }

    override fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>> {
        return object : NetworkBoundResource<ModuleEntity, ContentResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<ModuleEntity> =
                localDadaSource.getModuleWithContent(moduleId)

            override fun shouldFetch(data: ModuleEntity?): Boolean =
                data?.contentEntity == null

            override fun createCall(): LiveData<ApiResponse<ContentResponse>> =
                remoteDataSource.getContent(moduleId)

            override fun saveCallResult(data: ContentResponse) =
                localDadaSource.updateContent(data.content.toString(), moduleId)

        }.asLiveData()
    }

    override fun getBookmarkedCourses(): LiveData<List<CourseEntity>>  =
        localDadaSource.getBookMarkedCourse()

    override fun setCourseBookmark(course: CourseEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDadaSource.setCourseBookmark(course, state) }

    override fun setReadModule(module: ModuleEntity) =
        appExecutors.diskIO().execute { localDadaSource.setReadModule(module) }


}