package com.erazero1.tamyrym

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.erazero1.tamyrym.navigation.AppBottomBar
import com.erazero1.tamyrym.navigation.AppNavGraph
import com.erazero1.tamyrym.navigation.bottomNavItems
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.utils.showShortToast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination
            val showBottomBar =
                bottomNavItems.any { item ->
                    currentDestination?.hierarchy?.any { destination -> destination.hasRoute(item.route::class) } == true
                }

            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            AppBottomBar(navController = navController)
                        }
                    }) { innerPadding ->
                    var backPressedTime by remember { mutableLongStateOf(0L) }
                    val context = LocalContext.current

                    BackHandler {
                        if (backPressedTime + 2000 > System.currentTimeMillis()) {
                            (context as? Activity)?.finish()
                        } else {
                            showShortToast(context, context.getString(R.string.press_back_again))
                            backPressedTime = System.currentTimeMillis()
                        }
                    }

                    AppNavGraph(
                        modifier = Modifier
                            .padding(innerPadding),
                        navController = navController,
                    )
                }
            }
        }
    }
}