package com.erazero1.tamyrym

import android.app.Application
import com.erazero1.splash.presentation.splashModule
import core.data.local.localDataModule
import core.data.remote.remoteDataModule
import feature.auth.di.authFeatureModule
import feature.profile.di.profileModule
import feature.tree.di.treeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TamyrymApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TamyrymApp)
            modules(
                remoteDataModule, localDataModule, authFeatureModule, splashModule,
                profileModule, treeModule,
            )
        }
    }
}