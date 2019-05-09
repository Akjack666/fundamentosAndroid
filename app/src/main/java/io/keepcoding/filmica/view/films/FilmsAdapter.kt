package io.keepcoding.filmica.view.films

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.keepcoding.filmica.R
import io.keepcoding.filmica.data.Film
import io.keepcoding.filmica.view.util.SimpleTarget
import kotlinx.android.synthetic.main.item_film.view.*

class FilmsAdapter(val listener: (Film) -> Unit) :
    RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    private val films = mutableListOf<Film>()

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, type: Int): FilmViewHolder {
        val view = LayoutInflater.from(recyclerView.context).inflate(
            R.layout.item_film,
            recyclerView,
            false
        )

        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films.get(position)
        holder.film = film
    }

    fun setFilms(list: List<Film>) {
        this.films.clear()
        this.films.addAll(list)
        notifyDataSetChanged()
    }

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var film: Film? = null
            set(value) {
                field = value

                with(itemView) {
                    field?.let {
                        labelTitle.text = it.title
                        labelGenre.text = it.genre
                        labelRating.text = it.rating.toString()

                        loadImage(it)
                    }
                }
            }

        private fun loadImage(it: Film) {
            val target = SimpleTarget { bitmap: Bitmap ->
                itemView.imgPoster.setImageBitmap(bitmap)
                setColor(bitmap)
            }

            itemView.imgPoster.tag = target

            Picasso.with(itemView.context)
                .load(it.getPosterUrl())
                .error(R.drawable.placeholder)
                .into(target)
        }

        private fun setColor(bitmap: Bitmap) {
            Palette.from(bitmap).generate {
                val defaultColor =
                    ContextCompat.getColor(itemView.context, R.color.colorPrimary)
                val swatch = it?.vibrantSwatch ?: it?.dominantSwatch
                val color = swatch?.rgb ?: defaultColor

                itemView.container.setBackgroundColor(color)
                itemView.containerData.setBackgroundColor(color)
            }
        }

        init {
            itemView.setOnClickListener {
                film?.let {
                    listener.invoke(it)
                }
            }
        }
    }
}