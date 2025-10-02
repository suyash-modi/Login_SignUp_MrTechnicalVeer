package com.droid.loginsignup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droid.loginsignup.R
import com.droid.loginsignup.models.CategoryItem
import com.droid.loginsignup.viewModels.CategoryAdapter

class CategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    // List of categories with images
    private val categories = listOf(

        CategoryItem("Abstract", R.drawable.abs),
        CategoryItem("Aircraft", R.drawable.aircraft),
        CategoryItem("Animal", R.drawable.animal),
        CategoryItem("Anime", R.drawable.anime),
        CategoryItem("Red", R.drawable.red),
        CategoryItem("Avatar", R.drawable.avatar),
        CategoryItem("Avenue", R.drawable.avenue),
        CategoryItem("Beach", R.drawable.beach),
        CategoryItem("Bike", R.drawable.bike),
        CategoryItem("Birds eye view", R.drawable.bird_view),
        CategoryItem("Birds", R.drawable.birds),
        CategoryItem("Black & White", R.drawable.blackwhite),
        CategoryItem("Car", R.drawable.car),
        CategoryItem("Christmas", R.drawable.christmas),
        CategoryItem("Crimson Dreams", R.drawable.crimson_dreams),
        CategoryItem("Dragon", R.drawable.dragon),
        CategoryItem("Fall", R.drawable.fall),
        CategoryItem("Flower", R.drawable.flower),
        CategoryItem("Forest", R.drawable.forest),
        CategoryItem("Food", R.drawable.food),
        CategoryItem("Funny", R.drawable.funny),
        CategoryItem("Gradient", R.drawable.gradient),
        CategoryItem("Love", R.drawable.love),
        CategoryItem("Money", R.drawable.money),
        CategoryItem("Nature", R.drawable.nature),
        CategoryItem("Neon", R.drawable.neon),
        CategoryItem("Islamic", R.drawable.islamic),
        CategoryItem("Oil Painting", R.drawable.oil_painting),
        CategoryItem("Patterns", R.drawable.pattern),
        CategoryItem("Pixel Art", R.drawable.pixel_art),
        CategoryItem("Sweater Weather", R.drawable.sweater_weather),
        CategoryItem("Quotes", R.drawable.quotes),
        CategoryItem("Sad", R.drawable.sad),
        CategoryItem("Space", R.drawable.space),
        CategoryItem("Sports", R.drawable.sport),
        CategoryItem("Superheroes", R.drawable.superhero),
        CategoryItem("Tech", R.drawable.tech),
        CategoryItem("Typography", R.drawable.typography),
        CategoryItem("Valentines Day", R.drawable.valentine),
        CategoryItem("Video Game", R.drawable.game),
        CategoryItem("Winter", R.drawable.winter),
        CategoryItem("Woman", R.drawable.women),
        CategoryItem("Workout", R.drawable.workout),
        CategoryItem("3D Art", R.drawable.dd_art),
        CategoryItem("Baby", R.drawable.baby),
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewCategories)

        val adapter = CategoryAdapter(categories) { category ->
            openWallpapersFragment(category.name)
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        return view
    }

    private fun openWallpapersFragment(category: String) {
        val fragment = WallpapersFragment().apply {
            arguments = Bundle().apply {
                putString("category", category)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
