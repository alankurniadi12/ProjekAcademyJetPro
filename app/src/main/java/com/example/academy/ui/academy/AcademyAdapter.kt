package com.example.academy.ui.academy

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.academy.R
import com.example.academy.data.source.local.entity.CourseEntity
import com.example.academy.ui.detail.DetailCourseActivity
import kotlinx.android.synthetic.main.items_academy.view.*

class AcademyAdapter: RecyclerView.Adapter<AcademyAdapter.CourseViewModel>() {

    companion object {
        private val TAG = AcademyAdapter::class.java.simpleName
    }

    private var listCourses = ArrayList<CourseEntity>()

    fun setCourses(courses: List<CourseEntity>?) {
        if (courses == null) return
        listCourses.clear()
        listCourses.addAll(courses)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_academy, parent, false)
        return CourseViewModel(view)
    }

    override fun getItemCount(): Int = listCourses.size

    override fun onBindViewHolder(holder: CourseViewModel, position: Int) {
        val course = listCourses[position]
        holder.bind(course)
    }

    class CourseViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(course: CourseEntity){
            with(itemView){
                tv_item_title.text = course.title
                tv_item_description.text = course.description
                tv_item_date.text = itemView.resources.getString(R.string.deadline_date, course.deadline)

                Log.d(TAG, course.toString())

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(img_poster)
            }
        }
    }
}