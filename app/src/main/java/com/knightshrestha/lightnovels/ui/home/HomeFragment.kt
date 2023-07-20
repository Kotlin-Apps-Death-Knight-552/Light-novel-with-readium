package com.knightshrestha.lightnovels.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.databinding.FragmentHomeBinding
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel

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

    lateinit var listView: RecyclerView
    private var recyclerViewScrollPosition: Int = 0


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

        listView = binding.seriesListHome

        homeViewModel.seriesList.observe(viewLifecycleOwner) { list ->
            binding.seriesListHome.apply {
                adapter = SeriesListAdapter(list.sortedBy { it.seriesTitle })
            }
        }

        val act = activity as MainActivity

        if (act.navView != null) {
            act.navView!!.visibility = View.VISIBLE
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        recyclerViewScrollPosition = (listView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0
    }

    override fun onResume() {
        super.onResume()
        recyclerViewScrollPosition.apply {
            if (this != RecyclerView.NO_POSITION) {
                (listView.layoutManager as? LinearLayoutManager)?.scrollToPosition(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}