package feature.tree.di

import feature.tree.data.remote.PersonApi
import feature.tree.data.remote.TreeApi
import feature.tree.data.repository.PersonRepositoryImpl
import feature.tree.data.repository.TreeRepositoryImpl
import feature.tree.domain.repository.PersonRepository
import feature.tree.domain.repository.TreeRepository
import feature.tree.domain.usecase.AddRelationToPersonUseCase
import feature.tree.domain.usecase.CreateNewTreeUseCase
import feature.tree.domain.usecase.DeletePersonUseCase
import feature.tree.domain.usecase.DeleteTreeUseCase
import feature.tree.domain.usecase.GetOptimizedTreeGraphUseCase
import feature.tree.domain.usecase.GetPersonUseCase
import feature.tree.domain.usecase.GetPersonsByTreeId
import feature.tree.domain.usecase.GetTreeListUseCase
import feature.tree.domain.usecase.GetTreePersonsUseCase
import feature.tree.domain.usecase.UpdatePersonUseCase
import feature.tree.domain.usecase.UpdateTreeUseCase
import feature.tree.ui.tree_canvas.TreeCanvasViewModel
import feature.tree.ui.tree_list.TreeListViewModel
import org.koin.core.module.dsl.viewModel
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
    factory<GetPersonsByTreeId> {
        GetPersonsByTreeId(repository = get<PersonRepository>())
    }
    factory<DeletePersonUseCase> {
        DeletePersonUseCase(repository = get<PersonRepository>())
    }
    factory<GetPersonUseCase> {
        GetPersonUseCase(repository = get<PersonRepository>())
    }
    factory<UpdatePersonUseCase> {
        UpdatePersonUseCase(repository = get<PersonRepository>())
    }
    factory<GetTreePersonsUseCase> {
        GetTreePersonsUseCase(repository = get<PersonRepository>())
    }
    factory<AddRelationToPersonUseCase> {
        AddRelationToPersonUseCase(repository = get<TreeRepository>())
    }
    factory<GetOptimizedTreeGraphUseCase> {
        GetOptimizedTreeGraphUseCase(repository = get<TreeRepository>())
    }
    factory<CreateNewTreeUseCase> {
        CreateNewTreeUseCase(repository = get<TreeRepository>())
    }
    factory<DeleteTreeUseCase> {
        DeleteTreeUseCase(repository = get<TreeRepository>())
    }
    factory<GetTreeListUseCase> {
        GetTreeListUseCase(repository = get<TreeRepository>())
    }
    factory<UpdateTreeUseCase> {
        UpdateTreeUseCase(repository = get<TreeRepository>())
    }

    // ViewModels
    viewModel<TreeListViewModel> {
        TreeListViewModel(
            getTreeListUseCase = get<GetTreeListUseCase>(),
            createNewTreeUseCase = get<CreateNewTreeUseCase>(),
            updateTreeUseCase = get<UpdateTreeUseCase>(),
        )
    }

    viewModel<TreeCanvasViewModel> {
        TreeCanvasViewModel(
            getOptimizedTreeGraphUseCase = get<GetOptimizedTreeGraphUseCase>(),
        )
    }
}