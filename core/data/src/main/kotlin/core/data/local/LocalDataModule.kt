package core.data.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single {
        TokenManager(androidContext())
    }
}