package com.enzoroiz.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

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

        webView.apply {
            webViewClient = WebViewClient()
            if (article.url.isNullOrEmpty().not()) {
                loadUrl(article.url!!)
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.error_load_article_url_failed), Toast.LENGTH_LONG).show()
            }
        }
    }
}