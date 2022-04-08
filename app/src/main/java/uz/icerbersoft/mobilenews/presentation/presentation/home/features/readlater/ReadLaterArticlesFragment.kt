package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icerbersoft.mobilenews.R
import uz.icerbersoft.mobilenews.databinding.FragmentReadLaterArticlesBinding
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.controller.ReadLaterArticleItemController
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.di.ReadLaterArticlesDaggerComponent
import uz.icerbersoft.mobilenews.presentation.support.controller.StateEmptyItemController
import uz.icerbersoft.mobilenews.presentation.support.controller.StateErrorItemController
import uz.icerbersoft.mobilenews.presentation.support.controller.StateLoadingItemController
import uz.icerbersoft.mobilenews.presentation.utils.LoadingState.*
import uz.icerbersoft.mobilenews.presentation.utils.addCallback
import uz.icerbersoft.mobilenews.presentation.utils.onBackPressedDispatcher
import javax.inject.Inject

internal class ReadLaterArticlesFragment : Fragment(R.layout.fragment_read_later_articles) {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        ReadLaterArticlesDaggerComponent
            .create((requireActivity() as GlobalActivity).globalDaggerComponent)
            .inject(this)

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
        viewModel.articlesLiveData.observe(this) { state ->
            val itemList = ItemList.create()
            when (state) {
                is SuccessItem -> itemList.addAll(state.data, articleController)
                is EmptyItem -> itemList.add(stateEmptyItemController)
                is ErrorItem -> itemList.add(stateErrorController)
                is LoadingItem -> itemList.add(stateLoadingController)
            }
            easyAdapter.setItems(itemList)
        }
    }

    companion object {

        fun newInstance() =
            ReadLaterArticlesFragment()
    }
}