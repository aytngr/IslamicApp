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

class ChapterAdapter(private val mList: List<Chapter>,private val listener: OnItemClickListener? ) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = mList[position]
        holder.chapterNumber.text = chapter.number.toString() + "."
        holder.chapterName.text = chapter.name
        holder.chapterVerseCount.text = chapter.verses.toString() + " ayə"

        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                listener?.onChapterClick(chapter)

            }
        }

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val chapterNumber = itemView.findViewById<TextView>(R.id.chapter_number)
        val chapterName = itemView.findViewById<TextView>(R.id.chapter_name)
        val chapterVerseCount = itemView.findViewById<TextView>(R.id.chapter_verse_count)
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onChapterClick(chapter: Chapter)
    }
}