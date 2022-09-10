package com.elearning.rekamiacademy.ui.view.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.elearning.rekamiacademy.R
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.databinding.FragmentDetailBinding
import com.elearning.rekamiacademy.ui.viewmodel.DetailViewModel
import com.elearning.rekamiacademy.util.ImageLoader.loadImage
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setGone
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding
    private val args : DetailFragmentArgs by navArgs()
    private val viewModel : DetailViewModel by viewModels()
    private var stateFavoriteNews : Boolean = false
    private var data : ArticleEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnFavoriteBack?.setOnClickListener {
            if(args.isFavorite){
                Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_favoriteFragment2)
            }else{
                Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_listNewsFragment)
            }
        }
        hideContent()
        viewModel.getDetailNews(args.id).observe(viewLifecycleOwner,observer)
        binding?.btnSave?.setOnClickListener {
            data?.id?.let { id -> onFavButtonCLick(id) }
            lifecycleScope.launch(Dispatchers.IO){
                withContext(Dispatchers.Main){
                    setFavoriteState(stateFavoriteNews)
                    if(stateFavoriteNews){
                        Snackbar.make(view,"News has been save",Toast.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(view,"News has been remove",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun hideContent() = binding?.apply {
        shimmer.root.setVisible()
    }

    private fun showContent() = binding?.apply {
        shimmer.root.setGone()
    }

    private val observer = Observer<ArticleEntity> { result ->
        bindNews(result)
        showContent()
        lifecycleScope.launch(Dispatchers.IO){
            val count = viewModel.isFavorite(args.id)
            withContext(Dispatchers.Main){
                stateFavoriteNews = count > 0
                setFavoriteState(stateFavoriteNews)
            }
        }
    }

    private fun bindNews(it: ArticleEntity) {
        binding?.apply {
            tvDescription.text = it.content
            tvTitle.text = it.title
            tvHours.text = it.released
            tvWriter.text = it.author
            context?.let { it1 -> imgImageUrl.loadImage(it1,it.urlImage) }
        }
        data = it
    }

    private fun onFavButtonCLick(id : Int){
        stateFavoriteNews = if(stateFavoriteNews){
            lifecycleScope.launch(Dispatchers.IO){
                viewModel.deleteFavorite(id)
            }
            false
        }else{
            lifecycleScope.launch (Dispatchers.IO){
                data?.let { viewModel.addFavorite(it) }
            }
            true
        }
    }

    private fun setFavoriteState(stateFavoriteNews: Boolean) {
        if(stateFavoriteNews){
            binding?.btnSave?.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.save_button,null))
        } else{
            binding?.btnSave?.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.unsave_button,null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding?.root?.parent != null) (_binding?.root!!.parent as ViewGroup).removeView(
            _binding?.root)
    }
}