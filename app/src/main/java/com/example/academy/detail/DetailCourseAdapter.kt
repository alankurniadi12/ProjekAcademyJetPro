package com.example.academy.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academy.R
import com.example.academy.data.ModuleEntity
import kotlinx.android.synthetic.main.items_module_list.view.*

class DetailCourseAdapter: RecyclerView.Adapter<DetailCourseAdapter.ModuleViewModel>() {

    private val listModules = ArrayList<ModuleEntity>()

    fun setModules(modules: List<ModuleEntity>?) {
        if (modules == null) return
        listModules.clear()
        listModules.addAll(modules)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_module_list, parent, false)
        return ModuleViewModel(view)
    }

    override fun getItemCount(): Int = listModules.size

    override fun onBindViewHolder(holder: ModuleViewModel, position: Int) {
        val module = listModules[position]
        holder.bind(module)
    }

    inner class ModuleViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(module: ModuleEntity) {
            with(itemView) {
                text_module_title.text = module.title
            }
        }
    }

}