package id.riverflows.moviicatexp.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Content.Companion.TYPE_MOVIE
import id.riverflows.core.domain.model.Content.Companion.TYPE_TV
import id.riverflows.core.domain.model.Content.MovieTv
import id.riverflows.core.utils.AppConfig.POSTER_URL
import id.riverflows.core.utils.AppConfig.POSTER_URL_ORIGINAL
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ActivityDetailBinding
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel(){
        val data = intent.getParcelableExtra<MovieTv>(EXTRA_MOVIE_TV_DATA)
        if(data != null){
            if(data.type == TYPE_MOVIE){
                viewModel.getDetailMovie(data.id).observe(this){
                    processResponse(it)
                }
            }else{
                viewModel.getDetailTv(data.id).observe(this){
                    processResponse(it)
                }
            }
        }
    }

    private fun processResponse(response: Resource<MovieTv>){
        when(response){
            is Resource.Loading ->{
                setLoadingState(true)
            }
            is Resource.Success -> {
                setLoadingState(false)
                response.data?.run {
                    viewModel.updateData(this)
                    bindData(this)
                    Timber.d("Bind Data")
                }
                Timber.d(response.data.toString())
            }
            is Resource.Error -> {
                setLoadingState(false)
                Timber.d(response.toString())
                Timber.d(response.message)
            }
        }
    }

    private fun bindData(data: MovieTv){
        val date =
            if(data.type == TYPE_TV)
                "${data.releaseDate} - ${data.lastDate}"
            else data.releaseDate
        with(binding){
            tvTitle.text = data.title
            tvPopularity.text = data.popularity.toString()
            tvRate.text = data.voteAverage.toString()
            tvStatus.text = data.status
            tvDate.text = date
            tvValueOverview.text = data.overview
            Glide.with(this@DetailActivity)
                .load("$POSTER_URL_ORIGINAL/${data.posterPath}")
                .apply(RequestOptions()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_broken_image)
                )
                .into(ivPoster)
        }
    }

    private fun setLoadingState(isLoading: Boolean){
        with(binding){
            if(isLoading){
                viewContainer.visibility = View.INVISIBLE
                shimmerContainer.visibility = View.VISIBLE
                shimmerContainer.startShimmerAnimation()
            }else{
                viewContainer.visibility = View.VISIBLE
                shimmerContainer.visibility = View.INVISIBLE
                shimmerContainer.stopShimmerAnimation()
            }
        }
    }

    private fun setFabState(isFavorite: Boolean){
        with(binding.fabFavorite){
            if(isFavorite){
                setImageResource(R.drawable.ic_favorite)
            }else{
                setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}