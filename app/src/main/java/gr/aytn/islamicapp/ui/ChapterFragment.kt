package gr.aytn.islamicapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.AyatAdapter
import gr.aytn.islamicapp.adapters.ChapterAdapter
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.databinding.FragmentChapterBinding
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.prefs

@AndroidEntryPoint
class ChapterFragment : Fragment() {

    val quranViewModel: QuranViewModel by viewModels()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChapterBinding.inflate(inflater,container,false)
        val root = binding.root

        recyclerView = binding.ayatRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        quranViewModel.searchByChapterNo(prefs.chapter_no).observe(viewLifecycleOwner, Observer {
            val ayatAdapter = AyatAdapter(it)
            recyclerView.adapter = ayatAdapter
        })

        return root
    }

}