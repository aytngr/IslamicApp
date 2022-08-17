package gr.aytn.islamicapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.LocationCity

class LocationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        val locationsList = arrayListOf<String>("Agdash","Aghjabadi","Agstafa","Agsu","Astara","Aghdara","Babek","Baku" ,"Balakən","Barda","Beylagan",
            "Bilasuvar","Dashkasan","Shabran","Fuzuli","Gadabay","Ganja","Goranboy","Goychay","Goygol",
            "Hajigabul","Imishli","Ismayilli","Jabrayil","Julfa","Kalbajar","Khachmaz","Khankendi","Khojavend",
            "Khirdalan","Kurdamir","Lankaran","Lerik","Masally","Mingachevir","Nakhchivan","Naftalan","Neftchala",
            "Oghuz","Ordubad","Qabala","Qakh","Qazakh","Quba","Qubadli","Qusar","Saatlı","Sabirabad","Shahbuz","Shaki",
            "Shamakhi","Shamkir","Sharur","Shirvan","Siyazan","Shusha","Sumgait","Tartar","Tovuz","Ujar","Yardimli","Yevlakh","Zaqatala","Zardab","Zangilan")

        val locationRecyclerView: RecyclerView = findViewById(R.id.location_recyclerview)


    }
}