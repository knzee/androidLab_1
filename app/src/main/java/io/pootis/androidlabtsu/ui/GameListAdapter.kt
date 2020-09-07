package io.pootis.androidlabtsu.ui


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.pootis.androidlabtsu.data.Game


class GameListAdapter internal constructor(val context: Context)
    : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var games = emptyList<Game>()


    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameItemView: TextView = itemView.findViewById(io.pootis.androidlabtsu.R.id.textView)
        val gameImgUrl: ImageView = itemView.findViewById(io.pootis.androidlabtsu.R.id.imageView)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                // To pass any data to next activity
                intent.putExtra("name", games[adapterPosition].name)
                intent.putExtra("description", games[adapterPosition].description)
                intent.putExtra("imgUrl", games[adapterPosition].imgUrl)
                // start your next activity
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = inflater.inflate(io.pootis.androidlabtsu.R.layout.recyclerview_item, parent, false)

        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val current = games[position]
        holder.gameItemView.text = current.name

        val url = current.imgUrl
        Glide.with(context)
            .load(url)
            .into(holder.gameImgUrl)
    }

    internal fun setGames(words: List<Game>) {
        this.games = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = games.size
}