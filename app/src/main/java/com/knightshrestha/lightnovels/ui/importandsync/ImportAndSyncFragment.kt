package com.knightshrestha.lightnovels.ui.importandsync

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.knightshrestha.lightnovels.databinding.FragmentImportAndSyncBinding
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel
import java.io.File

class ImportAndSyncFragment : Fragment() {

    private var _binding: FragmentImportAndSyncBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment  No. of items in table series list
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val baseFilePath =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getString("root_path", "")

        _binding = FragmentImportAndSyncBinding.inflate(inflater, container, false)

        binding.syncSeriesBtn.text = "Import books from $baseFilePath"

        binding.syncSeriesBtn.setOnClickListener {
            if (File(baseFilePath).exists()) {
                File(baseFilePath!!).listFiles().forEach { folder->
                    if (folder.isDirectory) {
                        val epubFiles = folder.listFiles().filter { it.extension == "epub" }
                        if ( epubFiles.isNotEmpty()) {
                            epubFiles.forEach {
                                viewModel.addBookItem(
                                    BookItem(
                                        bookPath = it.absolutePath.removePrefix(folder.absolutePath),
                                        bookTitle = it.nameWithoutExtension,
                                        seriesPath = folder.absolutePath
                                    )
                                )
                            }
                            viewModel.addSeriesItem(
                                SeriesItem(
                                    seriesPath = folder.absolutePath,
                                    seriesTitle = folder.name
                                )
                            )
                        }
                    }
                }

            }
        }

        binding.deleteSeriesBtn.setOnClickListener {
            viewModel.seriesList.observe(viewLifecycleOwner) { list ->
                list.forEach {
                    if (!File(it.seriesPath).exists()) {
                        viewModel.deleteSeriesItemByPath(it.seriesPath)
                    }
                }
            }
            viewModel.bookList.observe(viewLifecycleOwner) {list ->
                list.forEach {
                    if (!File(it.bookPath).exists()) {
                        viewModel.deleteBookItem(it)
                    }
                }

            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}