package gr.aytn.islamicapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.NotificationService
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.AyatAdapter
import gr.aytn.islamicapp.adapters.ChapterAdapter
import gr.aytn.islamicapp.adapters.HorizontalChapterAdapter
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.databinding.FragmentChapterBinding
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.prefs

@AndroidEntryPoint
class ChapterFragment : Fragment(), HorizontalChapterAdapter.OnHorizontalItemClickListener {

    private var _binding: FragmentChapterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val quranViewModel: QuranViewModel by viewModels()
    var recyclerView: RecyclerView? = null
    var recyclerViewBottomChapters: RecyclerView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapterBinding.inflate(inflater,container,false)
        val root = binding.root

        recyclerView = binding.ayatRecyclerview
        recyclerView?.layoutManager = LinearLayoutManager(context)

        recyclerViewBottomChapters = binding.bottomChaptersRecyclerview
        recyclerViewBottomChapters?.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )

        (recyclerViewBottomChapters?.layoutManager as LinearLayoutManager).scrollToPosition(
            prefs.chapter_no-2);

        quranViewModel.getAllChapters().observe(viewLifecycleOwner, Observer {
            val horizontalChapterAdapter = HorizontalChapterAdapter(it,this)
            recyclerViewBottomChapters?.adapter = horizontalChapterAdapter
        })


        val tvChapterName = binding.name
        val tvClassication = binding.classification

        val searchView = binding.search

        val settings = binding.chapterSettings

        val currentChapterIndex = Constants.getChapterList().get(prefs.chapter_no-1)

        tvChapterName.text = currentChapterIndex.name + " surəsi"
        tvClassication.text = "${currentChapterIndex.classification}də endirilib ${currentChapterIndex.verses} ayədir."

        settings.setOnClickListener {
//            findNavController().navigate(R.id.chapterSettingsFragment)
            val settBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.chapter_settings_bottomsheet,null)
            settBottomSheet.setContentView(bottomSheetView)


            val translation = settBottomSheet.findViewById<TextView>(R.id.translation)
            val textSize = settBottomSheet.findViewById<TextView>(R.id.text_size)
            val readingLang = settBottomSheet.findViewById<TextView>(R.id.reading_style)

            translation!!.setOnClickListener {
//                settBottomSheet.hide()
//                val translationBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
                val translationBottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.translation_settings_bottomsheet,null)
                settBottomSheet.setContentView(translationBottomSheetView)

                val radioGroup = settBottomSheet.findViewById<RadioGroup>(R.id.RGroup)
                val translation1 = settBottomSheet.findViewById<RadioButton>(R.id.alikhan)
                val translation2 = settBottomSheet.findViewById<RadioButton>(R.id.vasim)

                when (prefs.selected_translation) {
                    "Əlixan Musayev" -> translation1?.isChecked = true
                    "Vasim Məmmədəliyev və Ziya Bünyadov" -> translation2?.isChecked = true
                }
                radioGroup?.setOnCheckedChangeListener { _, i ->
                    when (i) {
                        R.id.alikhan -> {prefs.selected_translation = "Əlixan Musayev"
                                         findNavController().navigate(R.id.chapterFragment)}
                        R.id.vasim -> {prefs.selected_translation = "Vasim Məmmədəliyev və Ziya Bünyadov"
                                       findNavController().navigate(R.id.chapterFragment)}
                    }
                }

                settBottomSheet.show()
                settBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
            }

            textSize?.setOnClickListener {
                val textSizeBottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.text_size_bottomsheet,null)
                settBottomSheet.setContentView(textSizeBottomSheetView)

                val seekbarAz = settBottomSheet.findViewById<SeekBar>(R.id.seekbar_az)
                val seekbarAr = settBottomSheet.findViewById<SeekBar>(R.id.seekbar_ar)

                seekbarAz?.progress = prefs.translation_font_size
                seekbarAr?.progress = prefs.arabic_font_size
                seekbarAz?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        if(progress<16){
                            prefs.translation_font_size = 16
                        }else{
                            prefs.translation_font_size = progress
                            recyclerView?.adapter?.notifyDataSetChanged()
                        }

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // you can probably leave this empty
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // you can probably leave this empty
                    }
                })
                seekbarAr?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        if(progress<16){
                            prefs.arabic_font_size = 16
                        }else{
                            prefs.arabic_font_size = progress
                            recyclerView?.adapter?.notifyDataSetChanged()
                        }

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // you can probably leave this empty
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // you can probably leave this empty
                    }
                })
            }

            readingLang?.setOnClickListener {
                val readingLangBottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.reading_lang_bottomsheet,null)
                settBottomSheet.setContentView(readingLangBottomSheetView)

                val radioGroup = settBottomSheet.findViewById<RadioGroup>(R.id.RGroup)
                val ar = settBottomSheet.findViewById<RadioButton>(R.id.ar)
                val az = settBottomSheet.findViewById<RadioButton>(R.id.az)
                val araz = settBottomSheet.findViewById<RadioButton>(R.id.araz)

                when (prefs.selected_text_language) {
                    "ar" -> ar?.isChecked = true
                    "az" -> az?.isChecked = true
                    "araz" -> araz?.isChecked = true
                }
                radioGroup?.setOnCheckedChangeListener { _, i ->
                    when (i) {
                        R.id.ar -> {
                            prefs.selected_text_language = "ar"
                                findNavController().navigate(R.id.chapterFragment)
                                recyclerView?.adapter?.notifyDataSetChanged()
                        }
                        R.id.az -> {
                            prefs.selected_text_language = "az"
                                findNavController().navigate(R.id.chapterFragment)
                                recyclerView?.adapter?.notifyDataSetChanged()
                        }
                        R.id.araz -> {
                            prefs.selected_text_language = "araz"
                            findNavController().navigate(R.id.chapterFragment)
                            recyclerView?.adapter?.notifyDataSetChanged()
                        }
                    }
                }

                settBottomSheet.show()
                settBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
            }


            settBottomSheet.show()
//            notfSettBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            settBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            calcSettBottomSheet.window?.setGravity(Gravity.BOTTOM)
        }


        searchView.inputType = InputType.TYPE_CLASS_NUMBER;
        searchView.queryHint = "1-${prefs.chapter_verse_count}"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.toInt()>=1 && query.toInt()<= prefs.chapter_verse_count){
                    (recyclerView?.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        query.toInt(), 20);
                    searchView.setQuery("",false)
                    searchView.isIconified = true
                }else{
                    Toast.makeText(context,"Rəqəm düzgün deyil",Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        quranViewModel.searchByChapterNo(prefs.chapter_no).observe(viewLifecycleOwner, Observer {
            (it as ArrayList).add(0,Ayat())
            val ayatAdapter = AyatAdapter(it)
            recyclerView?.adapter = ayatAdapter
        })

        return root
    }

    override fun onHorizontalChapterClick(chapter: Chapter) {
        findNavController().navigate(R.id.chapterFragment)
        prefs.chapter_no = chapter.number!!
        prefs.chapter_verse_count = chapter.verses!!

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        recyclerView = null
        recyclerViewBottomChapters = null
    }

}