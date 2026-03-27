package feature.tree.data.model.ai

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.ai.Insight
import kotlinx.serialization.Serializable

@Serializable
data class InsightDTO(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("related_ids") val relatedIds: List<String?>? = null,
)

fun InsightDTO.toDomain(): Insight {
    return Insight(
        title = this.title ?: "",
        description = this.description ?: "",
        type = this.type ?: "",
        relatedIds = this.relatedIds?.filterNotNull() ?: emptyList()
    )
}