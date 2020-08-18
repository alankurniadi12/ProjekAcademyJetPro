package com.example.academy.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.academy.R
import com.example.academy.data.source.local.entity.CourseEntity
import com.example.academy.ui.detail.DetailCourseActivity
import kotlinx.android.synthetic.main.items_bookmark.view.*

class BookmarkAdapter(private val callback: BookMarkFragmentCallback): RecyclerView.Adapter<BookmarkAdapter.CourseViewModel>() {
    private val listCourses = ArrayList<CourseEntity>()

    fun setCourse(courses: List<CourseEntity>?) {
        if (courses == null) return
        listCourses.clear()
        listCourses.addAll(courses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_bookmark, parent, false)
        return CourseViewModel(view)
    }

    override fun onBindViewHolder(holder: CourseViewModel, position: Int) {
        val course = listCourses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listCourses.size

    inner class CourseViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(course: CourseEntity){
            with(itemView){
                tv_item_title.text = course.title
                tv_item_description.text = course.description
                tv_item_date.text = resources.getString(R.string.deadline_date, course.deadline)

                setOnClickListener{
                    val intent = Intent(context, DetailCourseActivity::class.java).apply {
                        putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    }
                    context.startActivity(intent)
                }
                img_share.setOnClickListener{ callback.onShareClick(course)}
                Glide.with(context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(img_poster)
            }
        }
    }


}