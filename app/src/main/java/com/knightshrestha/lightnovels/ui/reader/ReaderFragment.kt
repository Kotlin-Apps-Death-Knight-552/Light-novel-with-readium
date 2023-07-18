package com.knightshrestha.lightnovels.ui.reader

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.navArgs
import com.knightshrestha.lightnovels.MainActivity
import com.knightshrestha.lightnovels.R
import com.knightshrestha.lightnovels.databinding.FragmentReaderBinding
import org.readium.r2.navigator.ExperimentalDecorator
import org.readium.r2.navigator.Navigator
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.asset.FileAsset
import org.readium.r2.streamer.parser.epub.EpubParser
import java.io.File


class ReaderFragment : Fragment(), EpubNavigatorFragment.Listener {
    private var _binding: FragmentReaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var publication: Publication
    lateinit var navigator: Navigator
    lateinit var navigatorFragment: EpubNavigatorFragment
    lateinit var ui: ConstraintLayout
    var actionBar: androidx.appcompat.app.ActionBar? = null


    private val args: ReaderFragmentArgs by navArgs()

    @OptIn(ExperimentalReadiumApi::class, ExperimentalDecorator::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        val activity = requireActivity() as MainActivity

        val file = File(args.bookPath)
        require(file.exists())
        val asset = FileAsset(file)


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


    @SuppressLint("ClickableViewAccessibility")
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


        binding.readerEpubContainer.setOnTouchListener (View.OnTouchListener {v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.d("touch", "touched")
                if (actionBar!!.isShowing) {
                    actionBar!!.hide()
                } else {
                    actionBar!!.show()
                }
                true
            } else false
        })


        navigator = childFragmentManager.findFragmentByTag(navigatorFragmentTag) as Navigator
        navigatorFragment = navigator as EpubNavigatorFragment



        return binding.root
    }

    override fun onJumpToLocator(locator: Locator) {
        super.onJumpToLocator(locator)
    }

    override fun onTap(point: PointF): Boolean {
        if (actionBar!!.isShowing) {
            actionBar!!.hide()
            binding.readerUi.visibility = View.GONE
        } else {
            actionBar!!.show()
            binding.readerUi.visibility = View.VISIBLE


        }
        return super.onTap(point)
    }
}