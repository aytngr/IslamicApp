package gr.aytn.islamicapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.AyatAdapter
import gr.aytn.islamicapp.adapters.ChapterAdapter
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.databinding.FragmentQuranBinding
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.prefs

@AndroidEntryPoint
class QuranFragment : Fragment(), ChapterAdapter.OnItemClickListener{

    val quranViewModel: QuranViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuranBinding.inflate(inflater,container,false)
        val root = binding.root


        val recyclerView = binding.verseRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val chapterAdapter = ChapterAdapter(Constants.getChapterList(),this)
        recyclerView.adapter = chapterAdapter

//        quranViewModel.searchByChapterNo(2).observe(viewLifecycleOwner, Observer {
//            val adapter = AyatAdapter(it)
//            recyclerView.adapter = adapter
//        })

        return root
    }
    override fun onChapterClick(chapter: Chapter) {
        findNavController().navigate(R.id.chapterFragment)
        prefs.chapter_no = chapter.number
    }




}