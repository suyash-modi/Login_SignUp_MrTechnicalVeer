package com.droid.loginsignup.viewModels

import com.droid.loginsignup.models.CategoryItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droid.loginsignup.R

class CategoryAdapter(private val categories: List<CategoryItem>, private val onClick: (CategoryItem) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        private val categoryText: TextView = itemView.findViewById(R.id.categoryText)


        fun bind(category: CategoryItem) {
            categoryText.text = category.name
            categoryImage.setImageResource(category.imageResId)
            itemView.setOnClickListener { onClick(category) }
        }
    }
}
