package uz.icerbersoft.mobilenews.data.model.article.wrapper

sealed class LoadingState<out T> {

    object LoadingItem : LoadingState<Nothing>()

    data class SuccessItem<T>(val data: T) : LoadingState<T>()

    object EmptyItem : LoadingState<Nothing>()

    object ErrorItem : LoadingState<Nothing>()
}
