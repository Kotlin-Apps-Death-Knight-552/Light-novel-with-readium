package com.knightshrestha.lightnovels.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.databinding.FragmentHomeBinding
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel
import java.io.File

class HomeFragment : Fragment() {
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("data", (data?.data?.path).toString())
//
//        val lol = data?.data?.path
//        if (lol != null) {
//            File("/storage/74A9-13E8/").listFiles()?.forEach { file ->
//                Log.d("data", file.name)
//
//
//            }
//        }
//    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseFilePath =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getString("root_path", "")

        if (baseFilePath != null && baseFilePath != "") {
            extracted(baseFilePath, homeViewModel)
        }



        homeViewModel.seriesList.observe(viewLifecycleOwner) { it ->
            binding.seriesListHome.apply {
                adapter = SeriesListAdapter(it)
            }
        }

        val act = activity as MainActivity

        if (act.navView != null) {
            act.navView!!.visibility = View.VISIBLE
        }

        return root
    }

    private fun extracted(
        baseFilePath: String,
        homeViewModel: MainViewModel
    ) {
        File(baseFilePath).listFiles()?.forEach { file ->
            homeViewModel.addSeriesItem(
                SeriesItem(
                    seriesTitle = file.name,
                    seriesPath = file.absolutePath,

                    )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}