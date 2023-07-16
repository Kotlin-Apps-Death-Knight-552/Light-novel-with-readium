package com.knightshrestha.lightnovels.ui.reader

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.knightshrestha.lightnovels.databinding.FragmentReaderBinding
import com.smartmobilefactory.epubreader.EpubScrollDirection
import com.smartmobilefactory.epubreader.model.Epub
import com.smartmobilefactory.epubreader.model.EpubLocation.XPathLocation
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


class ReaderFragment : Fragment() {
    private var _binding: FragmentReaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var epubSingle: Single<Epub>? = null
    private val TAG = "Hello_World"

//    private val args: DetailFragmentArgs by navArgs()

//    private lateinit var viewModel: DetailViewModel/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        binding.epubView.scrollDirection = EpubScrollDirection.HORIZONTAL_WITH_VERTICAL_CONTENT


        loadEpub()!!.doOnSuccess {epub: Epub? ->
            binding.epubView.epub = epub
            binding.epubView.gotoLocation(XPathLocation.fromXPath(9, "id(\"preface002\")/P[4]"))
        }.subscribe()



        observeEpub()

        return binding.root
    }

    fun loadEpub(): Single<Epub>? {
        if (epubSingle == null) {
            val application = activity?.application
            epubSingle = Single.fromCallable {
                Epub.fromUri(
                    application,
                    File("/storage/sdcard0/documents/sample.epub").toUri().toString()
                )
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
        }
        return epubSingle
    }

    private fun observeEpub() {
        binding.epubView.currentLocation()
            .doOnNext { xPathLocation ->
                Log.d(
                    TAG,
                    "CurrentLocation: $xPathLocation"
                )
            }.subscribe()
        binding.epubView.currentChapter()
            .doOnNext { chapter ->
                Log.d(
                    TAG,
                    "CurrentChapter: $chapter"
                )
            }.subscribe()
    }

}