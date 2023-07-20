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

        binding.syncSeriesBtn.setOnClickListener {
            if (baseFilePath != null && baseFilePath != "") {
                val folderList = listFolderRecursiveWithFolder(baseFilePath)

                folderList.forEach {
                        viewModel.addSeriesItem(
                            SeriesItem(
                                seriesPath = it.absolutePath,
                                seriesTitle = it.nameWithoutExtension
                            )
                        )

                    val fileList = it.listFiles()
                    fileList.forEach {epub->
                        if (epub.isFile && epub.name.endsWith(".epub", true)) {
                            viewModel.addBookItem(
                                BookItem(
                                    bookPath = epub.absolutePath,
                                    bookTitle = epub.nameWithoutExtension,
                                    seriesPath = it.absolutePath
                                )
                            )
                        }

                    }
                }




            }
        }

        return binding.root
    }

    private fun listFolderRecursiveWithFolder(path: String): List<File> {
        val folders = mutableListOf<File>()

        File(path).walkTopDown().forEach {file ->
            if (file.isDirectory) {
                val epubFiles = file.listFiles { _, name ->
                    name.endsWith(".epub", ignoreCase = true)
                }

                if (epubFiles != null && epubFiles.isNotEmpty()) {
                    folders.add(file)
                }
            }

        }

        return folders
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}