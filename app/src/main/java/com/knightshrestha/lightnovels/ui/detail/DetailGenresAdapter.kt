package com.knightshrestha.lightnovels.ui.detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knightshrestha.lightnovels.databinding.ItemGenresListBinding


class DetailGenresAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<DetailGenresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemGenresListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemGenresListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.itemGenreText
    }

}