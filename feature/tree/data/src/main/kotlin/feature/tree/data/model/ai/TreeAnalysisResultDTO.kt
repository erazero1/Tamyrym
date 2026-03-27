package feature.tree.data.model.ai

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.ai.TreeAnalysisResult
import kotlinx.serialization.Serializable

@Serializable
data class TreeAnalysisResultDTO(
    @SerializedName("tree_id") val treeId: String? = null,
    @SerializedName("insights") val insights: List<InsightDTO?>? = null,
)

fun TreeAnalysisResultDTO.toDomain(): TreeAnalysisResult {
    return TreeAnalysisResult(
        treeId = this.treeId ?: "",
        insights = this.insights?.filterNotNull()?.map { it.toDomain() } ?: emptyList()
    )
}