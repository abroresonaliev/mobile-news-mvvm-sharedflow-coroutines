package uz.icebergsoft.mobilenews.presentation.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import uz.icebergsoft.mobilenews.R
import uz.icebergsoft.mobilenews.databinding.FragmentArticleDetailBinding
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
        observeArticleDetail()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view)

        with(binding) {
            backIv.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }

        viewModel.setArticleId(checkNotNull(arguments?.getString(KEY_ARTICLE_ID)))
        viewModel.getArticleDetail()
    }

    private fun observeArticleDetail() {
        viewModel.articleDetailLiveData.observe(this) { state ->
            when (state) {
                is LoadingState -> {
                }
                is ErrorState -> {
                }
                is NotFoundState -> {
                }
                is SuccessState -> {
                    with(binding) {
                        detailImageSdv.setImageURI(state.data.imageUrl)
                        publishedAtTextView.text = state.data.publishedAt
                        titleTextView.text = state.data.title
                        sourceTextView.text = state.data.source.name
                        contentTextView.text = state.data.content

                        bookmarkIv.apply {
                            if (state.data.isBookmarked) setImageResource(R.drawable.ic_bookmark)
                            else setImageResource(R.drawable.ic_bookmark_border)
                        }

                        bookmarkIv.setOnClickListener { viewModel.updateBookmark(state.data) }

                        shareIv.setOnClickListener {
                            val shareText =
                                "${state.data.title}\n\nMobile news - interesting news in your mobile.\n\n${state.data.url}"

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