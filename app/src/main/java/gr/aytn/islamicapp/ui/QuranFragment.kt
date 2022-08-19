package gr.aytn.islamicapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.AyatAdapter
import gr.aytn.islamicapp.databinding.FragmentQuranBinding

@AndroidEntryPoint
class QuranFragment : Fragment() {

//    private lateinit var quranViewModel: QuranViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuranBinding.inflate(inflater,container,false)
        val root = binding.root

        val quranViewModel: QuranViewModel by viewModels()

        val recyclerView = binding.ayatRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        quranViewModel.getQuran()
        quranViewModel.searchByChapterNo(1).observe(viewLifecycleOwner, Observer {
            val adapter = AyatAdapter(it)
            recyclerView.adapter = adapter
        })

        return root
    }



}