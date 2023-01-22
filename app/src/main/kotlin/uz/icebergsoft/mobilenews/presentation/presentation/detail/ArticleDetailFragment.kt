package uz.icebergsoft.mobilenews.presentation.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.icebergsoft.mobilenews.R
import uz.icebergsoft.mobilenews.databinding.FragmentArticleDetailBinding
import uz.icebergsoft.mobilenews.domain.data.entity.article.Article
import uz.icebergsoft.mobilenews.presentation.global.GlobalActivity
import uz.icebergsoft.mobilenews.presentation.presentation.detail.di.ArticleDetailDaggerComponent
import uz.icebergsoft.mobilenews.presentation.support.event.LoadingEvent.*
import uz.icebergsoft.mobilenews.presentation.utils.addCallback
import uz.icebergsoft.mobilenews.presentation.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ArticleDetailViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ArticleDetailDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { viewModel.back() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view)

        with(binding) {
            backIv.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }

        subscribeSharedFlows()

        viewModel.setArticleId(checkNotNull(arguments?.getString(KEY_ARTICLE_ID)))
        viewModel.getArticleDetail()
    }

    private fun subscribeSharedFlows() {
        viewModel
            .articleDetailSharedFlow
            .onEach { state ->
                when (state) {
                    is SuccessState -> setLoadedArticle(state.data)
                    is LoadingState -> {}
                    is ErrorState -> {}
                    is NotFoundState -> {}
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setLoadedArticle(article: Article) {
        with(binding) {
            detailImageSdv.setImageURI(article.imageUrl)
            publishedAtTextView.text = article.publishedAt
            titleTextView.text = article.title
            sourceTextView.text = article.source.name
            contentTextView.text = article.content

            bookmarkIv.apply {
                if (article.isBookmarked) setImageResource(R.drawable.ic_bookmark)
                else setImageResource(R.drawable.ic_bookmark_border)
            }

            bookmarkIv.setOnClickListener { viewModel.updateBookmark(article) }

            shareIv.setOnClickListener {
                val shareText =
                    "${article.title}\n\nMobile news - interesting news in your mobile.\n\n${article.url}"

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share")
                startActivity(shareIntent)
            }
        }
    }

    companion object {

        private const val KEY_ARTICLE_ID: String = "key_article_id"

        fun newInstance(articleId: String) =
            ArticleDetailFragment().apply {
                arguments = Bundle().apply { putString(KEY_ARTICLE_ID, articleId) }
            }
    }
}