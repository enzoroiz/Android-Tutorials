package com.enzoroiz.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.databinding.FragmentNewsHeadlinesBinding
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.presentation.NewsAdapter
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModel
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    companion object {
        private const val DEFAULT_COUNTRY_CODE = "br"
        private const val SEARCH_DELAY = 3000L
        private const val PAGE_SIZE = 20
        private const val STARTING_PAGE = 1
    }

    private val viewModel by activityViewModels<NewsViewModel> { viewModelFactory }
    private lateinit var binding: FragmentNewsHeadlinesBinding
    private var page = STARTING_PAGE
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false

    private val adapter = NewsAdapter { article ->
        findNavController().navigate(
            NewsHeadlinesFragmentDirections.actionNewsHeadlinesFragmentToNewsFragment(article)
        )
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            isScrolling = true
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = binding.lstArticles.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val itemsDisplayed = layoutManager.childCount
            val listSize = layoutManager.itemCount
            val hasReachedListBottom = firstVisibleItemPosition + itemsDisplayed >= listSize

            if (hasReachedListBottom && isScrolling && isLoading.not() && isLastPage.not()) {
                page++
                isScrolling = false
                viewModel.getNewsHeadlines(page, DEFAULT_COUNTRY_CODE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewsHeadlinesBinding.bind(view)

        binding.lstArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.lstArticles.adapter = adapter
        binding.lstArticles.addOnScrollListener(onScrollListener)

        getNewsList()
        getSearchedNewsList()
    }

    private fun getNewsList() {
        viewModel.getNewsHeadlines(STARTING_PAGE, DEFAULT_COUNTRY_CODE)
        viewModel.getNewsHeadlinesLiveData.observe(viewLifecycleOwner, Observer {
            handleNewsResponse(it)
        })
    }

    private fun getSearchedNewsList() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var currentQuery: String? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchedNews(STARTING_PAGE, DEFAULT_COUNTRY_CODE, query.toString())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                currentQuery = query
                MainScope().launch {
                    delay(SEARCH_DELAY)
                    if (currentQuery == query) {
                        viewModel.getSearchedNews(
                            STARTING_PAGE,
                            DEFAULT_COUNTRY_CODE,
                            query.toString()
                        )
                    }
                }

                return false
            }
        })

        binding.searchView.setOnCloseListener {
            viewModel.getNewsHeadlines(STARTING_PAGE, DEFAULT_COUNTRY_CODE)
            false
        }

        viewModel.getSearchedNewsLiveData.observe(viewLifecycleOwner, Observer {
            handleNewsResponse(it)
        })
    }

    private fun handleNewsResponse(response: Resource<NewsResponse>) {
        when (response) {
            is Resource.Success -> {
                isLoading = false
                isLastPage = response.data?.totalResults?.let { totalResults ->
                    if (totalResults % PAGE_SIZE == 0) {
                        (totalResults / PAGE_SIZE) == page
                    } else {
                        (totalResults / PAGE_SIZE) + 1 == page
                    }
                } ?: false

                binding.progressBar.visibility = View.GONE
                adapter.differ.submitList(response.data?.articles)
            }

            is Resource.Error -> {
                isLoading = false
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    getString(R.string.news_headlines_fragment_message_get_news_list_failed),
                    Toast.LENGTH_LONG
                ).show()
            }

            is Resource.Loading -> {
                isLoading = true
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }
}