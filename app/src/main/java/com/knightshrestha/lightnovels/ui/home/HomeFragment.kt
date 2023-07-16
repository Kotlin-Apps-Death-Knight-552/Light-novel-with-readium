package com.knightshrestha.lightnovels.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.databinding.FragmentHomeBinding
import com.knightshrestha.lightnovels.localdatabase.helpers.Count
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import java.io.File

class HomeFragment : Fragment() {
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("data", (data?.data?.path).toString())

        val lol = data?.data?.path
        if (lol != null) {
            File("/storage/74A9-13E8/").listFiles()?.forEach { file ->
                Log.d("data", file.name)


            }
        }
    }

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
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseFilePath =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getString("root_path", "")

        if (baseFilePath != null) {
            File(baseFilePath).listFiles()?.map { it -> it.name }
        }




        binding.seriesListHome.apply {

            val list = File(baseFilePath).listFiles()?.filter { it.isDirectory }?.map { it ->
                SeriesItem(
                    seriesID = 0,
                    seriesTitle = it.name,
                    seriesPath = it.absolutePath,
                    seriesBooksCount = Count(0, 0, 0),
                    associatedTitles = emptyList(),
                    lastReadBook = 0,
                    seriesDownload = "",
                    seriesGenres = listOf("F")
                )
            }?.sortedBy {
                it.seriesTitle
            } ?: emptyList()
            if (baseFilePath != null) {
                adapter = SeriesListAdapter(
                    list
                )
            }
        }

        val act = activity as MainActivity

        if (act.navView != null) {
            act.navView!!.visibility = View.VISIBLE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}