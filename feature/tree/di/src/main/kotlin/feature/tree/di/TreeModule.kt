package feature.tree.di

import feature.tree.data.remote.PersonApi
import feature.tree.data.remote.TreeApi
import feature.tree.data.repository.PersonRepositoryImpl
import feature.tree.data.repository.TreeRepositoryImpl
import feature.tree.domain.repository.PersonRepository
import feature.tree.domain.repository.TreeRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val treeModule = module {
    single<TreeApi> {
        get<Retrofit>().create(TreeApi::class.java)
    }
    single<PersonApi> {
        get<Retrofit>().create(PersonApi::class.java)
    }

    single<TreeRepository> {
        TreeRepositoryImpl(
            treeApi = get<TreeApi>(),
        )
    }

    single<PersonRepository> {
        PersonRepositoryImpl(
            personApi = get<PersonApi>(),
        )
    }
}