package com.knightshrestha.lightnovels.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.knightshrestha.lightnovels.R
import com.knightshrestha.lightnovels.databinding.FragmentDashboardBinding
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.seriesList.observe(viewLifecycleOwner) {
            binding.dashSeriesCount.text = getString(R.string.dash_series_count, it.size, 0)
        }

        dashboardViewModel.bookList.observe(viewLifecycleOwner) {
            binding.dashBookCount.text = getString(R.string.dash_book_count, it.size)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}