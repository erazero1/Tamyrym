package feature.tree.domain.repository

import core.domain.result.ApiResult
import feature.tree.domain.model.AddRelationRequest
import feature.tree.domain.model.Person
import feature.tree.domain.model.Tree
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.model.TreeRequest

interface TreeRepository {
    suspend fun createNewTree(body: TreeRequest): ApiResult<Tree>
    suspend fun getTreeGraph(
        treeId: String,
        targetPersonId: String,
        depth: Int,
        preview: Boolean,
    ): ApiResult<TreeGraph>

    suspend fun getTreeList(): ApiResult<List<Tree>>
    suspend fun deleteTree(treeId: String): ApiResult<Unit>
    suspend fun updateTree(
        treeId: String,
        body: TreeRequest,
    ): ApiResult<Tree>

    suspend fun exportGedcom(treeId: String): ApiResult<Unit>
    suspend fun importGedcom(treeId: String): ApiResult<Unit>
    suspend fun addRelationToPerson(treeId: String, body: AddRelationRequest): ApiResult<Person>
}