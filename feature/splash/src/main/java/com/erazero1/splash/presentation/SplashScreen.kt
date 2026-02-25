package com.erazero1.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.erazero1.splash.R
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onGoToNextScreen: (isLoggedIn: Boolean) -> Unit,
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value

    Box(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.network_animation))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)

        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )

        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            onGoToNextScreen(isLoggedIn)
        }
    }
}