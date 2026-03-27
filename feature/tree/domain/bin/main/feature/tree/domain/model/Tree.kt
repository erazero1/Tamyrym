package feature.tree.domain.model

import java.time.Instant

data class Tree(
    val createdAt: Instant,
    val description: String,
    val id: String,
    val name: String,
    val ownerId: String,
    val updatedAt: Instant,
) {
    companion object {
        fun init() = Tree(
            createdAt = Instant.now(),
            description = "",
            id = "",
            name = "",
            ownerId = "",
            updatedAt = Instant.now()
        )
    }
}