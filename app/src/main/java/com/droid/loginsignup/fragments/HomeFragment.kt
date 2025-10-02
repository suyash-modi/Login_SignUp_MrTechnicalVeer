package com.droid.loginsignup.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.droid.loginsignup.R
import com.droid.loginsignup.viewModels.UnsplashAdapter
import com.droid.loginsignup.viewmodel.UnsplashViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var adView: AdView
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private val viewModel: UnsplashViewModel by viewModels()
    private lateinit var adapter: UnsplashAdapter
    private var searchJob: Job? = null
    private var isSearching = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adView = view.findViewById(R.id.adView)
        recyclerView = view.findViewById(R.id.recyclerView)
      //  searchBar = view.findViewById(R.id.searchBar)

        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adapter = UnsplashAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        observeData()

//        searchBar.doOnTextChanged { text, _, _, _ ->
//            searchJob?.cancel()
//            searchJob = lifecycleScope.launch {
//                delay(500)
//                val query = text.toString()
//                if (query.isNotEmpty()) {
//                    isSearching = true
//                    searchImages(query)
//                } else {
//                    if (isSearching) {
//                        isSearching = false
//                        loadDefaultImages()
//                    }
//                }
//            }
//        }

        return view
    }

    private fun loadDefaultImages() {
        lifecycleScope.launch {
            viewModel.getDefaultImages().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun searchImages(query: String) {
        lifecycleScope.launch {
            viewModel.searchImages(query).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.getDefaultImages().collectLatest { pagingData ->
                adapter.submitData(pagingData) // Uses cached data
            }
        }
    }

}