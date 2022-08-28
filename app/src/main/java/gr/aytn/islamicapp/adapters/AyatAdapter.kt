package gr.aytn.islamicapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.prefs


class AyatAdapter(private val mList: List<Ayat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder0(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvAyatOpeningArabic = itemView.findViewById<TextView>(R.id.tv_opening_arabic)
        val tvAyatOpening = itemView.findViewById<TextView>(R.id.tv_opening)
    }

    class ViewHolder2(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvAyatArabic = itemView.findViewById<TextView>(R.id.tv_ayat_arabic)
        val tvAyat = itemView.findViewById<TextView>(R.id.tv_ayat)
        val tvAyatNumber = itemView.findViewById<TextView>(R.id.tv_ayat_number)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        lateinit var view: View

        if (viewType == 1) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.opening_ayat, parent, false)
            return ViewHolder0(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.ayat, parent, false)
            return ViewHolder2(view)

        }


    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            val viewHolder0 = holder as ViewHolder0
            viewHolder0.tvAyatOpening.text = "Mərhəmətli, rəhmli Allahın adı ilə"
            viewHolder0.tvAyatOpeningArabic.text = "بِسۡمِ اللّٰهِ الرَّحۡمٰنِ الرَّحِيۡم"
        } else {
            val viewHolder2 = holder as ViewHolder2
            val ayat = mList[position]

            viewHolder2.tvAyatArabic.text = "${ayat.arabic}"
            viewHolder2.tvAyatArabic.textSize = prefs.arabic_font_size.toFloat()
            viewHolder2.tvAyat.textSize = prefs.translation_font_size.toFloat()
            if(prefs.selected_translation == "Əlixan Musayev"){
                viewHolder2.tvAyat.text = "${ayat.text_alikhan_musayev}"
            }else if(prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov"){
                viewHolder2.tvAyat.text = "${ayat.text}"
            }

            viewHolder2.tvAyatNumber.text = "${ayat.verse}. "

            if (prefs.selected_text_language == "az"){
                viewHolder2.tvAyatArabic.visibility = View.GONE
                viewHolder2.tvAyat.visibility = View.VISIBLE
            }else if(prefs.selected_text_language == "ar"){
                viewHolder2.tvAyat.visibility = View.GONE
                viewHolder2.tvAyatArabic.visibility = View.VISIBLE
            }else if(prefs.selected_text_language == "araz"){
                viewHolder2.tvAyat.visibility = View.VISIBLE
                viewHolder2.tvAyatArabic.visibility = View.VISIBLE
            }
        }
    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }

}