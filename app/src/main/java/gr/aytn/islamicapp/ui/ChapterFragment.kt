package gr.aytn.islamicapp.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
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
            prefs.settings_from_chapter = true
            findNavController().navigate(R.id.chapterSettingsFragment)
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