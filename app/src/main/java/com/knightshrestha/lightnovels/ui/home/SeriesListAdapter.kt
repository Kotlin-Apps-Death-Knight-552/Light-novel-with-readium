package com.knightshrestha.lightnovels.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.knightshrestha.lightnovels.databinding.ItemSeriesListBinding
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem


class SeriesListAdapter(
    private val values: List<SeriesItem>
) : RecyclerView.Adapter<SeriesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemSeriesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.seriesTitle
        holder.synopsis.text = ""
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment(
                item.seriesPath
            ))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemSeriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val synopsis = binding.synopsis
    }

}