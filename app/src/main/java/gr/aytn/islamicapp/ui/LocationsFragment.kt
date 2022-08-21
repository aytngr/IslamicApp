package gr.aytn.islamicapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.LocationAdapter
import gr.aytn.islamicapp.databinding.FragmentLocationsBinding

class LocationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLocationsBinding.inflate(inflater,container,false)
        val root = binding.root

        val locationsList = arrayListOf<String>("Agdash","Aghjabadi","Agstafa","Agsu","Astara","Aghdara","Babek","Baku" ,"Balakən","Barda","Beylagan",
            "Bilasuvar","Dashkasan","Shabran","Fuzuli","Gadabay","Ganja","Goranboy","Goychay","Goygol",
            "Hajigabul","Imishli","Ismayilli","Jabrayil","Julfa","Kalbajar","Khachmaz","Khankendi","Khojavend",
            "Khirdalan","Kurdamir","Lankaran","Lerik","Masally","Mingachevir","Nakhchivan","Naftalan","Neftchala",
            "Oghuz","Ordubad","Qabala","Qakh","Qazakh","Quba","Qubadli","Qusar","Saatlı","Sabirabad","Shahbuz","Shaki",
            "Shamakhi","Shamkir","Sharur","Shirvan","Siyazan","Shusha","Sumgait","Tartar","Tovuz","Ujar","Yardimli","Yevlakh","Zaqatala","Zardab","Zangilan")

        val locationRecyclerView: RecyclerView = binding.locationRecyclerview
        locationRecyclerView.layoutManager = LinearLayoutManager(context)
//        locationRecyclerView.adapter = LocationAdapter(locationsList)

        return root
    }


}