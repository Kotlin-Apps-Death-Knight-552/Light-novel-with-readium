package com.knightshrestha.lightnovels.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.databinding.FragmentDetailBinding
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesWithBooks
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var viewModel: DetailViewModel
    private var actionBar: androidx.appcompat.app.ActionBar? = null

    private lateinit var seriesWithBooks: SeriesWithBooks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = activity as MainActivity
        activity.navView?.visibility = View.GONE

        actionBar = activity.supportActionBar
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getSeriesItemWithBooks(args.seriesPath).observe(viewLifecycleOwner) {
            binding.bookListDetail.apply {
                adapter = DetailListAdapter(it.bookItems.sortedBy { it.bookTitle })
            }

            binding.seriesTitleDetail.text = it.seriesItem.seriesTitle
        }


//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}