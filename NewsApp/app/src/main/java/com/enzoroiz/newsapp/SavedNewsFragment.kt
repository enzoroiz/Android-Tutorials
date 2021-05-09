package com.enzoroiz.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enzoroiz.newsapp.databinding.FragmentSavedNewsBinding
import com.enzoroiz.newsapp.presentation.NewsAdapter
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModel
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private val viewModel by activityViewModels<NewsViewModel> { viewModelFactory }
    private lateinit var binding: FragmentSavedNewsBinding
    private val adapter = NewsAdapter {
        findNavController().navigate(
            SavedNewsFragmentDirections.actionSavedNewsFragmentToNewsFragment(it)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
        binding.lstArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.lstArticles.adapter = adapter

        viewModel.getSavedArticles().observe(viewLifecycleOwner, { articles ->
            adapter.differ.submitList(articles)
        })
    }
}