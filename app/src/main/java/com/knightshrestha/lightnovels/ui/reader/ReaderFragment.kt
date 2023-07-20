package com.knightshrestha.lightnovels.ui.reader

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.R
import com.knightshrestha.lightnovels.databinding.FragmentReaderBinding
import com.knightshrestha.lightnovels.localdatabase.helpers.Status
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.readium.r2.navigator.ExperimentalDecorator
import org.readium.r2.navigator.Navigator
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.asset.FileAsset
import org.readium.r2.shared.publication.services.positions
import org.readium.r2.streamer.parser.epub.EpubParser
import java.io.File


class ReaderFragment : Fragment(), EpubNavigatorFragment.Listener {
    private var _binding: FragmentReaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var publication: Publication
    private lateinit var navigator: Navigator
    private lateinit var navigatorFragment: EpubNavigatorFragment
    lateinit var ui: ConstraintLayout
    private var actionBar: androidx.appcompat.app.ActionBar? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var bookItem: BookItem


    private val args: ReaderFragmentArgs by navArgs()

    @OptIn(ExperimentalReadiumApi::class, ExperimentalDecorator::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        val activity = requireActivity() as MainActivity

        val file = File(args.bookPath)
        require(file.exists())
        val asset = FileAsset(file)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        val pub = EpubParser().parse(args.bookPath)?.publication
        if (pub != null) {
            publication = pub
        }



        actionBar = activity.supportActionBar
        actionBar?.title = publication.metadata.title

        childFragmentManager.fragmentFactory =
            EpubNavigatorFragment.createFactory(
                publication = publication,
                listener = this,
                config = EpubNavigatorFragment.Configuration().apply {
                    // Register the HTML template for our custom [DecorationStyleAnnotationMark].

                }
            )



        super.onCreate(savedInstanceState)
    }


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReaderBinding.inflate(inflater, container, false)

        val navigatorFragmentTag = getString(R.string.epub_navigator_tag)

        childFragmentManager.commitNow {
            add(
                binding.readerEpubContainer.id,
                EpubNavigatorFragment::class.java,
                Bundle(),
                navigatorFragmentTag
            )
        }

        navigator = childFragmentManager.findFragmentByTag(navigatorFragmentTag) as Navigator
        navigatorFragment = navigator as EpubNavigatorFragment


        viewLifecycleOwner.lifecycleScope.launch {
            val total = publication.positions().count()
            binding.readerProgressbar.max = total

            navigator.currentLocator.collect {
                val current = it.locations.position ?: 0
                binding.readerProgressbar.progress = current
                binding.readerProgressLabel.text = "$current out of $total Position"
            }

        }

        return binding.root
    }

    override fun onTap(point: PointF): Boolean {
        Log.d("touch", point.toString())
        val width = navigatorFragment.view?.width?.toFloat() ?: 500f
        when (point.x) {
            in 0f..(width/3f) -> {
                navigator.goBackward(animated = true)
                actionBar!!.hide()
                binding.readerUi.visibility = View.VISIBLE
            }
            in (width/3f)..(2 * width/3f) -> anim()
            in (2 * width/3f)..(width) -> {
                navigator.goForward(animated = true)
                actionBar!!.hide()
                binding.readerUi.visibility = View.VISIBLE
            }
        }

        return super.onTap(point)
    }

    private fun anim() {
        if (actionBar!!.isShowing) {
            actionBar!!.hide()
            binding.readerUi.visibility = View.GONE
        } else {
            actionBar!!.show()
            binding.readerUi.visibility = View.VISIBLE


        }
    }

    fun onBackButtonPressed() {
        viewModel.updateBookItem(
            BookItem(
                bookPath = bookItem.bookPath,
                bookTitle = bookItem.bookTitle,
                locator = navigator.currentLocator.value,
                seriesPath = bookItem.seriesPath,
                status = Status.READING
            )
        )
    }

}