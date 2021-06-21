package id.riverflows.moviicatexp.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig.POSTER_URL_ORIGINAL
import id.riverflows.core.utils.State
import id.riverflows.core.utils.State.*
import id.riverflows.core.utils.UtilConstants.EXTRA_ID
import id.riverflows.core.utils.UtilConstants.EXTRA_TYPE
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInitialView()
        observeViewModel()
        requestData()
    }

    private fun setupInitialView(){
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFabState(false)
    }

    private fun requestData(){
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        val type = intent.getIntExtra(EXTRA_TYPE, 0)
        if(type == TYPE_MOVIE){
            viewModel.getDetailMovie(id)
        }else{
            viewModel.getDetailTv(id)
        }
        Timber.d("ID: $id, type: $type")
    }

    private fun observeViewModel(){
        viewModel.data.observe(this@DetailActivity){
            processResponse(it)
        }
    }

    private fun processResponse(response: Resource<MovieTv>){
        when(response){
            is Resource.Loading ->{
                setState(LOADING)
            }
            is Resource.Success -> {
                setState(SUCCESS)
                response.data?.run {
                    bindData(this)
                    Timber.d("Bind Data")
                    binding.fabFavorite.setOnClickListener {
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
            is Resource.Error -> {
                setState(ERROR)
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
    }

    private fun setState(state: State){
        with(binding){
            when(state){
                LOADING -> {
                    viewContainer.visibility = View.GONE
                    shimmerContainer.visibility = View.VISIBLE
                    shimmerContainer.startShimmer()
                }
                SUCCESS -> {
                    viewContainer.visibility = View.VISIBLE
                    shimmerContainer.visibility = View.GONE
                    shimmerContainer.stopShimmer()
                }
                ERROR -> {
                    viewContainer.visibility = View.GONE
                    shimmerContainer.visibility = View.GONE
                    shimmerContainer.stopShimmer()
                }
                else -> return
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