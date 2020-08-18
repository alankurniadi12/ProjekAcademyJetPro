package com.example.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.academy.R
import com.example.academy.data.source.local.entity.ModuleEntity
import com.example.academy.data.vo.Status
import com.example.academy.ui.reader.CourseReaderViewModel
import com.example.academy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_module_content.*

/**
 * A simple [Fragment] subclass.
 */
class ModuleContentFragment : Fragment() {

    companion object {
        val TAG = ModuleContentFragment::class.java.simpleName

        fun newInstance(): ModuleContentFragment {
            return ModuleContentFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            viewModel.selectedModule.observe(viewLifecycleOwner, Observer { moduleEntity ->
                if (moduleEntity != null) {
                    when(moduleEntity.status) {
                        Status.LOADING -> progress_bar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            progress_bar.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        web_view.loadData(module.contentEntity?.content, "text/html", "UTF-8")
    }

}
