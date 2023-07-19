package com.knightshrestha.lightnovels.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.knightshrestha.lightnovels.databinding.ItemDetailListBinding
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem


class DetailListAdapter(
    private val values: List<BookItem>
) : RecyclerView.Adapter<DetailListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemDetailListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.bookTitle
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToReaderFragment(
                item.bookPath
            ))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemDetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val synopsis = binding.synopsis
    }

}