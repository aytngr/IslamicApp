package gr.aytn.islamicapp.ui

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val searchView = binding.searchChapter

        val recyclerView = binding.verseRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(
            prefs.chapter_no-3);

        quranViewModel.getAllChapters().observe(viewLifecycleOwner, Observer {
            val chapterAdapter = ChapterAdapter(it,this)
            recyclerView.adapter = chapterAdapter
        })


        searchView.inputType = InputType.TYPE_CLASS_TEXT;
        searchView.queryHint = "məs. Bəqərə"


        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                quranViewModel.searchChapterByName(query.toString().trim()).observe(viewLifecycleOwner,
                    Observer {
                        if(it != null){
                            findNavController().navigate(R.id.chapterFragment)
                            prefs.chapter_no = it.number!!
                            prefs.chapter_verse_count = it.verses!!
                            searchView.setQuery("", false)
                            searchView.isIconified = true
                        } else {
                            Toast.makeText(context, "Surə adı düzgün deyil", Toast.LENGTH_SHORT).show()
                        }
                    })

                return false
            }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })

//        quranViewModel.searchByChapterNo(2).observe(viewLifecycleOwner, Observer {
//            val adapter = AyatAdapter(it)
//            recyclerView.adapter = adapter
//        })

        return root
    }
    override fun onChapterClick(chapter: Chapter) {
        findNavController().navigate(R.id.chapterFragment)
        prefs.chapter_no = chapter.number!!
        prefs.chapter_verse_count = chapter.verses!!
    }




}