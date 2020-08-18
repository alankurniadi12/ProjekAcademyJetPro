package com.example.academy.data.source

//class FakeAcademyRepository (private val remoteDataSource: RemoteDataSource): AcademyDataSource{
//
//    override fun getAllCourses(): LiveData<List<CourseEntity>> {
//        val courseResult = MutableLiveData<List<CourseEntity>>()
//        remoteDataSource.getAllCourse(object : RemoteDataSource.LoadCourseCallback {
//            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
//                val courseList = ArrayList<CourseEntity>()
//                for (response in courseResponse) {
//                    val course = CourseEntity(
//                        response.id,
//                        response.title,
//                        response.description,
//                        response.date,
//                        false,
//                        response.imagePath
//                    )
//                    courseList.add(course)
//                }
//                courseResult.postValue(courseList)
//            }
//        })
//        return courseResult
//    }
//
//
//    override fun getBookmarkedCourses(): LiveData<List<CourseEntity>> {
//        val courseResult = MutableLiveData<List<CourseEntity>>()
//        remoteDataSource.getAllCourse(object : RemoteDataSource.LoadCourseCallback {
//            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
//                val courseList = ArrayList<CourseEntity>()
//                for (response in courseResponse) {
//                    val course = CourseEntity(
//                        response.id,
//                        response.title,
//                        response.description,
//                        response.date,
//                        false,
//                        response.imagePath
//                    )
//                    courseList.add(course)
//                }
//                courseResult.postValue(courseList)
//            }
//        })
//        return courseResult
//    }
//
//    override fun getCourseWiithModules(courseId: String): LiveData<CourseEntity> {
//        val courseResult = MutableLiveData<CourseEntity>()
//        remoteDataSource.getAllCourse(object : RemoteDataSource.LoadCourseCallback {
//            override fun onAllCoursesReceived(courseResponse: List<CourseResponse>) {
//                lateinit var course: CourseEntity
//                for (response in courseResponse) {
//                    if (response.id == courseId) {
//                        course = CourseEntity(
//                            response.id,
//                            response.title,
//                            response.description,
//                            response.date,
//                            false,
//                            response.imagePath
//                        )
//                    }
//                }
//                courseResult.postValue(course)
//            }
//        })
//        return courseResult
//
//    }
//
//    override fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> {
//        val moduleResults = MutableLiveData <List<ModuleEntity>>()
//        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModuleCallback {
//            override fun onAllModulesReceived(moduleResponse: List<ModuleResponse>) {
//                val moduleList = ArrayList<ModuleEntity>()
//                for (response in moduleResponse) {
//                    val course = ModuleEntity(
//                        response.moduleId,
//                        response.courseId,
//                        response.title,
//                        response.position,
//                        false
//                    )
//                    moduleList.add(course)
//                }
//                moduleResults.postValue(moduleList)
//            }
//
//        })
//        return moduleResults
//
//    }
//
//    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
//        val moduleResults = MutableLiveData<ModuleEntity>()
//
//        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModuleCallback {
//            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
//                lateinit var module: ModuleEntity
//                for (response in moduleResponses) {
//                    if (response.moduleId == moduleId) {
//                        module = ModuleEntity(
//                            response.moduleId,
//                            response.courseId,
//                            response.title,
//                            response.position,
//                            false
//                        )
//                        remoteDataSource.getContent(moduleId, object : RemoteDataSource.LoadContentCallback {
//                            override fun onContentReceived(contentResponse: ContentResponse) {
//                                module.contentEntity = ContentEntity(contentResponse.content)
//                                moduleResults.postValue(module)
//                            }
//                        })
//                        break
//                    }
//                }
//            }
//        })
//        return moduleResults
//
//    }
//}