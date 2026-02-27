package feature.tree.data.model

import com.erazero1.utils.toInstantOrNull
import core.domain.model.UnionType
import feature.tree.domain.model.Union
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UnionDTO(
    @SerialName("end_date")
    val endDate: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("person_1_id")
    val person1Id: String?,
    @SerialName("person_2_id")
    val person2Id: String?,
    @SerialName("place")
    val place: String?,
    @SerialName("start_date")
    val startDate: String?,
    @SerialName("start_date_string")
    val startDateString: String?,
    @SerialName("union_type")
    val unionType: String?
)

fun UnionDTO.toDomain(): Union {
    return Union(
        endDate = this.endDate.toInstantOrNull() ?: Instant.now(),
        id = this.id.orEmpty(),
        person1Id = this.person1Id.orEmpty(),
        person2Id = this.person2Id.orEmpty(),
        place = this.place.orEmpty(),
        startDate = this.startDate.toInstantOrNull() ?: Instant.now(),
        startDateString = this.startDateString.orEmpty(),
        unionType = UnionType.findValue(this.unionType)
    )
}