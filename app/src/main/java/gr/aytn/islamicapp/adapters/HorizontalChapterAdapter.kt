package gr.aytn.islamicapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.ui.ChapterFragment
import gr.aytn.islamicapp.ui.QuranFragment

class HorizontalChapterAdapter(private val mList: List<Chapter>, private val listener: OnHorizontalItemClickListener? ) : RecyclerView.Adapter<HorizontalChapterAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_chapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = mList[position]
        holder.chapterName.text = chapter.name

        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                listener?.onHorizontalChapterClick(chapter)
            }
        }

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val chapterName = itemView.findViewById<TextView>(R.id.horizontal_chapter_name)
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnHorizontalItemClickListener {
        fun onHorizontalChapterClick(chapter: Chapter)
    }
}