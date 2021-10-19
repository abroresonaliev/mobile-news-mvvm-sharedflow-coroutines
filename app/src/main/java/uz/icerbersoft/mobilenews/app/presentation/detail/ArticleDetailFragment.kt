package uz.icerbersoft.mobilenews.app.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import uz.icerbersoft.mobilenews.app.R
import uz.icerbersoft.mobilenews.app.databinding.FragmentArticleDetailBinding
import uz.icerbersoft.mobilenews.app.presentation.detail.di.ArticleDetailDaggerComponent
import uz.icerbersoft.mobilenews.app.utils.addCallback
import uz.icerbersoft.mobilenews.app.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail),
    IHasComponent<ArticleDetailDaggerComponent> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ArticleDetailViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentArticleDetailBinding

    override fun getComponent(): ArticleDetailDaggerComponent =
        ArticleDetailDaggerComponent.create(XInjectionManager.findComponent())

    override fun onCreate(savedInstanceState: Bundle?) {
        XInjectionManager.bindComponent(this).inject(this)
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { viewModel.back() }
        observeArticleDetail()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view)

        viewModel.setArticleId(checkNotNull(arguments?.getString(KEY_ARTICLE_ID)))
        viewModel.getArticleDetail()
    }

    private fun observeArticleDetail() {
        viewModel.articleDetailLiveData.observe(this) { resource ->
            when (resource) {
                is ArticleDetailResource.Loading -> {
                }
                is ArticleDetailResource.Failure -> {
                }
                is ArticleDetailResource.Success -> {
                    with(binding) {
                        imageSimpleImageView.setImageURI(resource.article.imageUrl)
                        publishedAtTextView.text = resource.article.publishedAt
                        titleTextView.text = resource.article.title
                        sourceTextView.text = resource.article.source.name
                        contentTextView.text = resource.article.content

                        shareButton.setOnClickListener {
                            val shareText =
                                "${resource.article.title}\n\nMobile news - interesting news in your mobile.\n\n${resource.article.url}"

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