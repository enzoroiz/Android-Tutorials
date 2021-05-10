package com.enzoroiz.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.databinding.FragmentNewsBinding
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModel
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private val viewModel by activityViewModels<NewsViewModel> { viewModelFactory }
    private val args: NewsFragmentArgs by navArgs()
    private lateinit var article: Article
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            if (article.url.isEmpty().not()) {
                loadUrl(article.url)
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.news_fragment_message_load_article_url_failed), Toast.LENGTH_LONG).show()
            }
        }

        binding.btnSaveArticle.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(it, getString(R.string.news_fragment_message_article_saved), Snackbar.LENGTH_LONG).show()
        }
    }
}