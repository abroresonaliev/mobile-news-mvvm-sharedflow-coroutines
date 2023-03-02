package uz.icebergsoft.mobilenews.domain.data.model.source

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source(

    @SerialName("id")
    val id: String?,

    @SerialName("name")
    val name: String
)