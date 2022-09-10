package com.elearning.rekamiacademy.ui.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elearning.rekamiacademy.R
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.remote.Result
import com.elearning.rekamiacademy.databinding.FragmentListNewsBinding
import com.elearning.rekamiacademy.ui.adapter.NewsForYouAdapter
import com.elearning.rekamiacademy.ui.adapter.NewsHeadlineAdapter
import com.elearning.rekamiacademy.ui.view.profile.ProfileFragmentDirections
import com.elearning.rekamiacademy.ui.viewmodel.DashboardViewModel
import com.elearning.rekamiacademy.util.Constant.NEWSFORYOU
import com.elearning.rekamiacademy.util.Constant.NEWSHEADLINE
import com.elearning.rekamiacademy.util.NavHelper.safeNavigate
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setGone
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setInvisible
import com.elearning.rekamiacademy.util.ViewVisibiltyUtil.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListNewsFragment: Fragment(), NewsForYouAdapter.NewsForYouCallback,
    NewsHeadlineAdapter.NewsHeadlineCallback {
    private var _binding : FragmentListNewsBinding? = null
    private val binding get() = _binding
    private lateinit var newsForYouAdapter : NewsForYouAdapter
    private lateinit var newsTopHeadlineAdapter: NewsHeadlineAdapter

    private val viewModel : DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentListNewsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.imgFavorite?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listNewsFragment_to_favoriteFragment)
        }
        setupForYouAdapter()
        setupTopHeadlineAdapter()
        toProfile()
    }

    private fun toProfile() {
        val direction = ListNewsFragmentDirections.actionListNewsFragmentToProfileFragment()
        safeNavigate(direction,javaClass.name)
    }

    private fun setupForYouAdapter(){
        newsForYouAdapter = NewsForYouAdapter(this)
        hideContent()
        binding?.apply {
            rvForYou.layoutManager = LinearLayoutManager(requireContext())
            rvForYou.hasFixedSize()
            rvForYou.adapter = newsForYouAdapter
        }
        viewModel.dataNewsForYou.observe(viewLifecycleOwner,observer)
    }

    fun setupTopHeadlineAdapter(){
        newsTopHeadlineAdapter = NewsHeadlineAdapter(this)
        hideContent()
        binding?.apply {
            rvTodayNews.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            rvTodayNews.hasFixedSize()
            rvTodayNews.adapter = newsTopHeadlineAdapter
        }
        viewModel.dataNewsHeadLine.observe(viewLifecycleOwner,observer)
    }

        private val observer = Observer<Result<List<ArticleEntity>>>{ result ->
        when(result){
            is Result.Success -> {
                showContent()
                result.data?.let {
                    val newsTopLineResult = it.filter { item -> item.type == NEWSHEADLINE }
                    newsTopHeadlineAdapter.submitList(newsTopLineResult)
                    val newsForYouResult = it.filter { item -> item.type == NEWSFORYOU }
                    newsForYouAdapter.submitList(newsForYouResult)
                }
            }
            is Result.Error -> {
                hideContent()
            }
        }
    }

    private fun hideContent() = _binding?.apply {
        shimerForYou.root.setVisible()
        rvForYou.setInvisible()
        shimmerHeadLine.root.setVisible()
        rvTodayNews.setInvisible()
    }

    private fun showContent()  = binding?.apply {
        shimerForYou.root.setGone()
        rvForYou.setVisible()
        shimmerHeadLine.root.setGone()
        rvTodayNews.setVisible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding?.root?.parent != null) (_binding?.root!!.parent as ViewGroup).removeView(
            _binding?.root)
    }

    override fun onClickForYou(article: ArticleEntity) {
        val toDetail = article.id?.let {
            ListNewsFragmentDirections.actionListNewsFragmentToDetailFragment(it)
        }
        safeNavigate(toDetail,javaClass.name)
    }

    override fun onClickTopHeadline(article: ArticleEntity) {
        val toDetail = article.id?.let {
            ListNewsFragmentDirections.actionListNewsFragmentToDetailFragment(it)
        }
        safeNavigate(toDetail,javaClass.name)
    }
}