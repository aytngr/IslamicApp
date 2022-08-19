package gr.aytn.islamicapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Ayat

class AyatAdapter(private val mList: List<Ayat>) : RecyclerView.Adapter<AyatAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ayat, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ayat = mList[position]
        holder.tvAyat.text = "${ayat.verse}. ${ayat.text}"

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvAyat = itemView.findViewById<TextView>(R.id.tv_ayat)
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
}