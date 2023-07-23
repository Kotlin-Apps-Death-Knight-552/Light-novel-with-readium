package com.knightshrestha.lightnovels.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.R
import com.knightshrestha.lightnovels.databinding.FragmentDetailBinding
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesWithBooks
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel
import com.knightshrestha.lightnovels.utils.MetadataFile
import java.io.File
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var viewModel: MainViewModel
    private var actionBar: androidx.appcompat.app.ActionBar? = null

    private lateinit var seriesWithBooks: SeriesWithBooks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = activity as MainActivity
        activity.navView?.visibility = View.GONE
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        actionBar = activity.supportActionBar
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_fragment_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.detail_menu_sync -> {
                val metaFile = File(seriesWithBooks.seriesItem.seriesPath + "/metadata.json")
                Log.d("exist", metaFile.exists().toString() + " " + seriesWithBooks.seriesItem.seriesPath + "/metadata.json")
                if (metaFile.exists()) {
                    val inpStr = metaFile.readText()
                    val listType: Type = object : TypeToken<MetadataFile>() {}.type
                    val metaObject: MetadataFile =  Gson().fromJson(inpStr, listType)

                    viewModel.updateSeriesItem(
                        seriesWithBooks.seriesItem.copy(
                            seriesAuthor = metaObject.author,
                            seriesGenres = metaObject.genres,
                            seriesSynopsis = metaObject.synopsis
                        )
                    )
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSeriesItemWithBooks(args.seriesPath).observe(viewLifecycleOwner) {
            seriesWithBooks = it

            binding.seriesTitleDetail.text = it.seriesItem.seriesTitle
            binding.seriesSynopsisDetail.text = it.seriesItem.seriesSynopsis


            binding.bookListDetail.apply {
                setHasFixedSize(false)
                adapter = DetailListAdapter(it.bookItems.sortedBy { it.bookTitle })
            }



            binding.genreListDetail.apply {
                val layoutManager = FlexboxLayoutManager(requireContext())
                layoutManager.apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.FLEX_START
                    flexWrap = FlexWrap.WRAP
                }

                this.layoutManager = layoutManager


                adapter = DetailGenresAdapter(
                    it.seriesItem.seriesGenres
                )
            }
        }


//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        return root
    }

}