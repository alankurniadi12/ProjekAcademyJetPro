package com.example.academy.utils

import android.content.Context
import android.util.Log
import com.example.academy.data.source.remote.response.ContentResponse
import com.example.academy.data.source.remote.response.CourseResponse
import com.example.academy.data.source.remote.response.ModuleResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private val TAG = JsonHelper::class.java.simpleName

    private fun parsingFiletoString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadCourses(): List<CourseResponse> {
        val list = ArrayList<CourseResponse>()

        try {
            val responseObject = JSONObject(parsingFiletoString("CourseResponses.json").toString())
            val listArray = responseObject.getJSONArray("courses")
            for (i in 0 until listArray.length()){
                val course = listArray.getJSONObject(i)

                val id = course.getString("id")
                val title = course.getString("title")
                val description = course.getString("description")
                val date = course.getString("date")
                val imagePath = course.getString("imagePath")

                val courseResponse = CourseResponse(id, title, description, date, imagePath)
                list.add(courseResponse)
                Log.d(TAG, title.toString())
            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadModule(courseId: String): List<ModuleResponse> {
        val fileName = String.format("Module_%s.json", courseId)
        val list = ArrayList<ModuleResponse>()
        try {
            val result = parsingFiletoString(fileName)
            if (result != null) {
                val responseObject = JSONObject(result)
                val listArray = responseObject.getJSONArray("modules")
                for (i in 0 until listArray.length()) {
                    val course = listArray.getJSONObject(i)

                    val moduleId = course.getString("moduleId")
                    val title = course.getString("title")
                    val position = course.getString("position")
                    val courseResponse = ModuleResponse(
                        moduleId,
                        courseId,
                        title,
                        Integer.parseInt(position))
                    list.add(courseResponse)
                    Log.e("JsonHelper", "Hasil Parsing: $courseResponse")
                    Log.e("JsonHelper", "loadModule list: $list")
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadContent(moduleId: String): ContentResponse {
        val fileName = String.format("Content_%s.json", moduleId)
        var contentResponse: ContentResponse? = null
        try {
            val result = parsingFiletoString(fileName)
            if (result != null) {
                val responseObject = JSONObject(result)
                val content = responseObject.getString("content")
                contentResponse = ContentResponse(moduleId, content)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return contentResponse as ContentResponse
    }

}