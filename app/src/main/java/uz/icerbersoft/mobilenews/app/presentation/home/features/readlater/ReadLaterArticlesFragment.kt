package uz.icerbersoft.mobilenews.app.presentation.home.features.readlater

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icerbersoft.mobilenews.app.R
import uz.icerbersoft.mobilenews.app.databinding.FragmentReadLaterArticlesBinding
import uz.icerbersoft.mobilenews.app.presentation.common.model.ArticleWrapper
import uz.icerbersoft.mobilenews.app.presentation.home.features.readlater.controller.ReadLaterArticleItemController
import uz.icerbersoft.mobilenews.app.presentation.home.features.readlater.di.ReadLaterArticlesDaggerComponent
import uz.icerbersoft.mobilenews.app.support.controller.StateEmptyItemController
import uz.icerbersoft.mobilenews.app.support.controller.StateErrorItemController
import uz.icerbersoft.mobilenews.app.support.controller.StateLoadingItemController
import uz.icerbersoft.mobilenews.app.utils.addCallback
import uz.icerbersoft.mobilenews.app.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class ReadLaterArticlesFragment : Fragment(R.layout.fragment_read_later_articles),
    IHasComponent<ReadLaterArticlesDaggerComponent> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ReadLaterArticlesViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentReadLaterArticlesBinding

    private val easyAdapter = EasyAdapter()
    private val articleController = ReadLaterArticleItemController(
        itemClickListener = { viewModel.openArticleDetailScreen(it.articleId) },
        bookmarkListener = { }
    )
    private val stateLoadingController = StateLoadingItemController(true)
    private val stateEmptyItemController = StateEmptyItemController(true)
    private val stateErrorController =
        StateErrorItemController(true) { viewModel.getReadLaterArticles() }


    override fun getComponent(): ReadLaterArticlesDaggerComponent =
        ReadLaterArticlesDaggerComponent.create(XInjectionManager.findComponent())

    override fun onCreate(savedInstanceState: Bundle?) {
        XInjectionManager.bindComponent(this).inject(this)
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) { viewModel.back() }
        observeArticleList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadLaterArticlesBinding.bind(view)

        with(binding) {
            readLaterArticlesRv.adapter = easyAdapter
            readLaterArticlesRv.itemAnimator = null
        }

        if (savedInstanceState == null)
            viewModel.getReadLaterArticles()
    }

    private fun observeArticleList() {
        viewModel.articlesLiveData.observe(this) { wrappers ->
            val itemList = ItemList.create()
            for (item in wrappers) {
                when (item) {
                    is ArticleWrapper.ArticleItem -> itemList.add(item, articleController)
                    is ArticleWrapper.EmptyItem -> itemList.add(stateEmptyItemController)
                    is ArticleWrapper.ErrorItem -> itemList.add(stateErrorController)
                    is ArticleWrapper.LoadingItem -> itemList.add(stateLoadingController)
                }
            }
            easyAdapter.setItems(itemList)
        }
    }

    companion object {

        fun newInstance() =
            ReadLaterArticlesFragment()
    }
}