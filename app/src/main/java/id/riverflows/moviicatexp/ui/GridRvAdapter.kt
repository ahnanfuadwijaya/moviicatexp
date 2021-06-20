package id.riverflows.moviicatexp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig.POSTER_URL
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ItemMovieTvGridBinding
import id.riverflows.moviicatexp.ui.GridRvAdapter.MovieGridViewHolder

class GridRvAdapter: RecyclerView.Adapter<MovieGridViewHolder>() {
    private val list = mutableListOf<MovieTv>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder(
            ItemMovieTvGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setList(data: List<MovieTv>){
        if(list.size > 0) list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MovieGridViewHolder(private val binding: ItemMovieTvGridBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: MovieTv){
            val context = itemView.context
            with(binding){
                tvTitle.text = data.title
                tvScore.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
                Glide.with(context)
                    .load("$POSTER_URL/${data.posterPath}")
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(ivPoster)
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data.id) }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(id: Long)
    }
}