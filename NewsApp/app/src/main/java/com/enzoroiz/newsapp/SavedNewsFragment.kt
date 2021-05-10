package com.enzoroiz.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.newsapp.databinding.FragmentSavedNewsBinding
import com.enzoroiz.newsapp.presentation.NewsAdapter
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModel
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
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

    private val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val article = adapter.differ.currentList[viewHolder.adapterPosition]
            viewModel.deleteSavedArticle(article)
            Snackbar.make(requireView(), getString(R.string.saved_news_fragment_message_article_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.saved_news_fragment_button_undo_label)) {
                    viewModel.saveArticle(article)
                }
                .show()
        }

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

        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.lstArticles)

        viewModel.getSavedArticles().observe(viewLifecycleOwner, { articles ->
            adapter.differ.submitList(articles)
        })
    }
}