package com.droid.loginsignup.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.droid.loginsignup.R
import com.droid.loginsignup.viewModels.UnsplashAdapter
import com.droid.loginsignup.viewmodel.UnsplashViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WallpapersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: UnsplashViewModel by viewModels()
    private lateinit var adapter: UnsplashAdapter
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category") ?: "random"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_wallpapers, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewW)

        adapter = UnsplashAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        loadCategoryImages(category!!)

        return view
    }

    private fun loadCategoryImages(category: String) {
        lifecycleScope.launch {
            viewModel.searchImages(category).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
