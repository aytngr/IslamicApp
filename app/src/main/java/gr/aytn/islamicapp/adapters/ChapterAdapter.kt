package gr.aytn.islamicapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
        holder.chapterNumber.text = chapter.number.toString()
        holder.chapterName.text = chapter.name
        holder.chapterVerseCount.text = chapter.verses.toString() + " ayə"

        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                listener?.onChapterClick(chapter)

            }
        }
//        if(chapter.favorite == true){
//            holder.lavFavorite.progress = 0F;
//            holder.lavFavorite.pauseAnimation();
//            holder.lavFavorite.speed = 2F
//            holder.lavFavorite.playAnimation();
//            holder.favorited = true
//        }else{
//            holder.lavFavorite.progress = 0F;
//            holder.lavFavorite.pauseAnimation();
//            holder.favorited = false
//        }
//        holder.lavFavorite.setOnClickListener {
//            if(holder.favorited){
//                holder.lavFavorite.progress = 0F;
//                holder.lavFavorite.pauseAnimation();
//                chapter.favorite = false
//                holder.favorited = false
//            }else{
//                holder.lavFavorite.progress = 0F;
//                holder.lavFavorite.pauseAnimation();
//                holder.lavFavorite.playAnimation();
//                chapter.favorite = true
//                holder.favorited = true
//
//
//            }
//        }
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val chapterNumber = itemView.findViewById<TextView>(R.id.chapter_number)
        val chapterName = itemView.findViewById<TextView>(R.id.chapter_name)
        val chapterVerseCount = itemView.findViewById<TextView>(R.id.chapter_verse_count)
//        val lavFavorite = itemView.findViewById<LottieAnimationView>(R.id.lav_favorite)
        var favorited: Boolean = false
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    interface OnItemClickListener {
        fun onChapterClick(chapter: Chapter)
    }
}