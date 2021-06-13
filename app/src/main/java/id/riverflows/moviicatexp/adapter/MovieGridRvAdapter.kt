package id.riverflows.moviicatexp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.riverflows.core.domain.model.Movie
import id.riverflows.core.utils.AppConfig.POSTER_URL
import id.riverflows.moviicatexp.adapter.MovieGridRvAdapter.*
import id.riverflows.moviicatexp.databinding.ItemMovieTvGridBinding

class MovieGridRvAdapter: RecyclerView.Adapter<MovieGridViewHolder>() {
    private val list = mutableListOf<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder(
            ItemMovieTvGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setList(data: List<Movie>){
        if(list.size > 0) list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MovieGridViewHolder(private val binding: ItemMovieTvGridBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Movie){
            val context = itemView.context
            with(binding){
                tvTitle.text = data.title
                tvScore.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
                Glide.with(context)
                    .load("$POSTER_URL/${data.posterPath}")
                    .into(ivPoster)
            }
        }
    }
}