package com.knightshrestha.lightnovels.ui.reader
//
//import android.R
//import android.os.Build
//import android.os.Bundle
//import android.os.StrictMode
//import android.os.StrictMode.ThreadPolicy
//import android.os.StrictMode.VmPolicy
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.webkit.WebView
//import android.widget.SeekBar
//import android.widget.SeekBar.OnSeekBarChangeListener
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.smartmobilefactory.epubreader.EpubScrollDirection
//import com.smartmobilefactory.epubreader.model.Epub
//import com.smartmobilefactory.epubreader.model.EpubFont
//import com.smartmobilefactory.epubreader.model.EpubLocation
//import com.smartmobilefactory.epubreader.sample.databinding.ActivityMainBinding
//import io.reactivex.Single
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//
//class MainActivity : AppCompatActivity() {
//    private var nightmodePlugin: NightmodePlugin? = null
//    private var tableOfContentsAdapter: TableOfContentsAdapter? = null
//    private var binding: ActivityMainBinding? = null
//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableStrictMode()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.getRoot())
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
//        }
//        initToolbar()
//        initSettingsContainer()
//        val bridge = ChapterJavaScriptBridge()
//        binding.epubView.getSettings().setJavascriptBridge(bridge)
//        binding.epubView.getSettings().setCustomChapterScript(bridge.getCustomChapterScripts())
//        binding.epubView.getSettings().setFont(EpubFont.fromFontFamily("Monospace"))
//        binding.epubView.setScrollDirection(EpubScrollDirection.HORIZONTAL_WITH_VERTICAL_CONTENT)
//        nightmodePlugin = NightmodePlugin(binding.epubView)
//        binding.epubView.addPlugin(nightmodePlugin)
//        tableOfContentsAdapter = TableOfContentsAdapter()
//        tableOfContentsAdapter.bindToEpubView(binding.epubView)
//        binding.contentsRecyclerView.setLayoutManager(LinearLayoutManager(this))
//        binding.contentsRecyclerView.setAdapter(tableOfContentsAdapter)
//        tableOfContentsAdapter.jumpToChapter()
//            .doOnNext { chapter ->
//                binding.drawerLayout.closeDrawer(Gravity.START)
//                binding.epubView.gotoLocation(EpubLocation.fromChapter(chapter))
//            }
//            .subscribe()
//        loadEpub()!!.doOnSuccess { epub: Epub? ->
//            binding.epubView.setEpub(epub)
//            tableOfContentsAdapter.setEpub(epub)
//            if (savedInstanceState == null) {
//                binding.epubView.gotoLocation(EpubLocation.fromChapter(10))
//            }
//        }.subscribe()
//        observeEpub()
//    }
//
//    fun loadEpub(): Single<Epub>? {
//        if (epubSingle == null) {
//            val application = application
//            epubSingle = Single.fromCallable {
//                Epub.fromUri(
//                    application,
//                    "file:///android_asset/The Silver Chair.epub"
//                )
//            }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .cache()
//        }
//        return epubSingle
//    }
//
//    private fun initToolbar() {
//        binding.toolbar.inflateMenu(R.menu.menu)
//        binding.toolbar.setOnMenuItemClickListener { item ->
//            when (item.getItemId()) {
//                R.id.menu_settings -> {
//                    if (binding.settings.getVisibility() === View.VISIBLE) {
//                        binding.settings.setVisibility(View.GONE)
//                    } else {
//                        binding.settings.setVisibility(View.VISIBLE)
//                    }
//                    return@setOnMenuItemClickListener true
//                }
//
//                else -> return@setOnMenuItemClickListener false
//            }
//        }
//        binding.toolbar.setNavigationOnClickListener { v -> binding.drawerLayout.openDrawer(Gravity.START) }
//    }
//
//    private fun initSettingsContainer() {
//
//        // TEXT SIZE
//        binding.textSizeSeekbar.setMax(30)
//        binding.textSizeSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//                binding.epubView.getSettings().setFontSizeSp(progress + 10)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar) {}
//        })
//
//        // DISPLAY FONT
//        binding.diplomata.setOnClickListener { v ->
//            binding.epubView.getSettings().setFont(
//                EpubFont.fromUri(
//                    "DiplomataSC",
//                    "file:///android_asset/fonts/Diplomata_SC/DiplomataSC-Regular.ttf"
//                )
//            )
//        }
//        binding.monospace.setOnClickListener { v ->
//            binding.epubView.getSettings().setFont(EpubFont.fromFontFamily("Monospace"))
//        }
//        binding.serif.setOnClickListener { v ->
//            binding.epubView.getSettings().setFont(EpubFont.fromFontFamily("Serif"))
//        }
//        binding.sanSerif.setOnClickListener { v ->
//            binding.epubView.getSettings().setFont(EpubFont.fromFontFamily("Sans Serif"))
//        }
//
//        // DISPLAY STRATEGY
//        binding.horizontalVerticalContent.setOnClickListener { v ->
//            binding.epubView.setScrollDirection(
//                EpubScrollDirection.HORIZONTAL_WITH_VERTICAL_CONTENT
//            )
//        }
//        binding.verticalVerticalContent.setOnClickListener { v ->
//            binding.epubView.setScrollDirection(
//                EpubScrollDirection.VERTICAL_WITH_VERTICAL_CONTENT
//            )
//        }
//        binding.singleChapterVertical.setOnClickListener { v ->
//            binding.epubView.setScrollDirection(
//                EpubScrollDirection.SINGLE_CHAPTER_VERTICAL
//            )
//        }
//        binding.nightmode.setOnCheckedChangeListener { buttonView, isChecked ->
//            nightmodePlugin.setNightModeEnabled(
//                isChecked
//            )
//        }
//    }
//
//    private fun observeEpub() {
//        binding.epubView.currentLocation()
//            .doOnNext { xPathLocation ->
//                Log.d(
//                    TAG,
//                    "CurrentLocation: $xPathLocation"
//                )
//            }.subscribe()
//        binding.epubView.currentChapter()
//            .doOnNext { chapter ->
//                Log.d(
//                    TAG,
//                    "CurrentChapter: $chapter"
//                )
//            }.subscribe()
//    }
//
//    private fun enableStrictMode() {
//        StrictMode.setThreadPolicy(
//            ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .build()
//        )
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .build()
//        )
//    }
//
//    companion object {
//        private var epubSingle: Single<Epub>? = null
//        private val TAG = MainActivity::class.java.simpleName
//    }
//}