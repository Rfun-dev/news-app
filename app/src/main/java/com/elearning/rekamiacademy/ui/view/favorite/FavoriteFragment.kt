package com.elearning.rekamiacademy.ui.view.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elearning.rekamiacademy.R
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity
import com.elearning.rekamiacademy.databinding.FragmentFavoriteBinding
import com.elearning.rekamiacademy.ui.adapter.NewsForYouAdapter
import com.elearning.rekamiacademy.ui.adapter.NewsHeadlineAdapter
import com.elearning.rekamiacademy.ui.view.detail.DetailFragmentDirections
import com.elearning.rekamiacademy.ui.viewmodel.FavoriteViewModel
import com.elearning.rekamiacademy.util.Constant
import com.elearning.rekamiacademy.util.NavHelper.safeNavigate
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setGone
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setInvisible
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : Fragment(), NewsForYouAdapter.NewsForYouCallback {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel : FavoriteViewModel by viewModels()
    private var favoriteAdapter : NewsForYouAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnFavoriteBack?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_favoriteFragment_to_listNewsFragment)
        }
        favoriteAdapter = NewsForYouAdapter(this)
        hideContent()
        binding?.apply {
            rvFavoriteList.layoutManager = LinearLayoutManager(requireContext())
            rvFavoriteList.hasFixedSize()
            rvFavoriteList.adapter = favoriteAdapter
        }
        viewModel.getFavorite().observe(viewLifecycleOwner,observer)
        showContent()
    }

    private fun showContent() = binding?.apply {
        shimmer.root.setInvisible()
        rvFavoriteList.setVisible()
    }

    private fun hideContent()  = binding?.apply {
        shimmer.root.setVisible()
        rvFavoriteList.setInvisible()
    }

    private val observer = Observer<List<FavoriteEntity>> { result ->
        bindNews(result)
    }

    private fun bindNews(result: List<FavoriteEntity>?) {
        result.let {
            val listFavorite : List<ArticleEntity>? = it?.map {  data ->
                ArticleEntity(
                    data.id,
                    data.author,
                    data.title,
                    data.content,
                    data.urlImage,
                    data.released,
                    ""
                )
            }
            favoriteAdapter?.submitList(listFavorite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding?.root?.parent != null) (_binding?.root!!.parent as ViewGroup).removeView(
            _binding?.root)
    }

    override fun onClickForYou(article: ArticleEntity) {
        val actionDetail = article.id?.let {
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it,true)
        }
        safeNavigate(actionDetail,javaClass.name)
    }
}