package feature.tree.domain.model

import core.domain.model.UnionType
import java.time.Instant

data class Union(
    val endDate: Instant,
    val id: String,
    val person1Id: String,
    val person2Id: String,
    val place: String,
    val startDate: Instant,
    val startDateString: String,
    val unionType: UnionType
) {
    companion object {
        fun init() = Union(
            endDate = Instant.now(),
            id = "",
            person1Id = "",
            person2Id = "",
            place = "",
            startDate = Instant.now(),
            startDateString = "",
            unionType = UnionType.OTHER
        )
    }
}

