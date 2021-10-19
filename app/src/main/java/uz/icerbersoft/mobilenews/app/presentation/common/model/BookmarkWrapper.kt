package uz.icerbersoft.mobilenews.app.presentation.common.model

sealed class BookmarkWrapper {

    object SuccessItem : BookmarkWrapper()

    object ErrorItem : BookmarkWrapper()
}
