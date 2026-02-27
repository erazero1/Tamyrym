package feature.tree.data.repository

import core.domain.result.ApiResult
import core.domain.result.map
import feature.tree.data.model.toDTO
import feature.tree.data.model.toDomain
import feature.tree.data.remote.TreeApi
import feature.tree.domain.model.Tree
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.model.TreeRequest
import feature.tree.domain.repository.TreeRepository

class TreeRepositoryImpl(
    private val treeApi: TreeApi,
) : TreeRepository {
    override suspend fun createNewTree(body: TreeRequest): ApiResult<Tree> {
        return treeApi.createTree(body = body.toDTO()).map { it.toDomain() }
    }

    override suspend fun getTreeGraph(
        treeId: String,
        targetPersonId: String,
        depth: Int,
        preview: Boolean,
    ): ApiResult<TreeGraph> {
        return treeApi.getTreeGraph(
            treeId = treeId,
            targetPersonId = targetPersonId,
            depth = depth,
            preview = preview,
        ).map { it.toDomain() }
    }

    override suspend fun getTreeList(): ApiResult<List<Tree>> {
        return treeApi.getTreeList().map { it.toDomain() }
    }

    override suspend fun deleteTree(treeId: String): ApiResult<Unit> {
        return treeApi.deleteTree(treeId = treeId)
    }

    override suspend fun updateTree(
        treeId: String,
        body: TreeRequest,
    ): ApiResult<Tree> {
        return treeApi.updateTree(
            treeId = treeId,
            body = body.toDTO(),
        ).map { it.toDomain() }
    }

    override suspend fun exportGedcom(treeId: String): ApiResult<Unit> {
        return treeApi.exportGedcom(treeId = treeId)
    }

    override suspend fun importGedcom(treeId: String): ApiResult<Unit> {
        return treeApi.importGedcom(treeId = treeId)
    }
}