package id.riverflows.moviicatexp.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig.POSTER_URL_ORIGINAL
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.core.utils.UtilConstants.TYPE_TV
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ActivityDetailBinding
import id.riverflows.moviicatexp.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var data: MovieTv? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInitialView()
        requestData()
    }

    private fun setupInitialView(){
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFabState(false)
        binding.fabFavorite.setOnClickListener {
            data?.run {
                this.isFavorite = !this.isFavorite
                viewModel.updateData(this)
                setFabState(this.isFavorite)
                val message = if(this.isFavorite){
                    getString(R.string.message_added_to_favorites)
                }else{
                    getString(R.string.message_removed_from_favorites)
                }
                Utils.showIndefiniteSnackBar(binding.root, message)
            }
        }
    }

    private fun requestData(){
        data = intent.getParcelableExtra(EXTRA_MOVIE_TV_DATA)
        observeViewModel()
    }

    private fun observeViewModel(){
        data?.run {
            if(type == TYPE_MOVIE){
                viewModel.getDetailMovie(id).observe(this@DetailActivity){
                    processResponse(it)
                }
            }else{
                viewModel.getDetailTv(id).observe(this@DetailActivity){
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
                    bindData(this)
                    Timber.d("Bind Data")
                }
                Timber.d(response.data.toString())
            }
            is Resource.Error -> {
                setLoadingState(false)
                response.message?.run {
                    Utils.showIndefiniteSnackBar(binding.root, this)
                }
            }
        }
    }

    private fun bindData(data: MovieTv){
        val date =
            if(data.type == TYPE_TV)
                "${data.releaseDate} - ${data.lastDate}"
            else
                data.releaseDate

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
            setFabState(data.isFavorite)
        }
        this.data = data
    }

    private fun setLoadingState(isLoading: Boolean){
        with(binding){
            if(isLoading){
                viewContainer.visibility = View.INVISIBLE
                shimmerContainer.visibility = View.VISIBLE
                shimmerContainer.startShimmer()
            }else{
                viewContainer.visibility = View.VISIBLE
                shimmerContainer.visibility = View.INVISIBLE
                shimmerContainer.stopShimmer()
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