package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Category

class CategoryAdapter(private val categoryList: List<Category>?):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var onItemClickCategory: (Category)-> Unit = {

    }
    inner class CategoryViewHolder(viewCategory: View):RecyclerView.ViewHolder(viewCategory){
        val tvCategory = viewCategory.findViewById<TextView>(R.id.tvCategory)
        init {
            tvCategory.setOnClickListener {
                onItemClickCategory.invoke(categoryList?.get(layoutPosition) ?: Category(0,""))
            }
        }
        fun onBindCategory(category: Category){
            tvCategory.text = category.categoryMovie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewCategory = inflater.inflate(R.layout.item_category,parent,false)
        return CategoryViewHolder(viewCategory)
    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBindCategory(categoryList?.get(position) ?: Category(0,"") )
    }
}