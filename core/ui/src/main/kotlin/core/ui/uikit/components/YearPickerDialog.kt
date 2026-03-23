package core.ui.uikit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.presentation.R
import core.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun YearPickerDialog(
    onDismissRequest: () -> Unit,
    onYearSelected: (Int) -> Unit,
    initialYear: Int? = null,
    yearRange: IntRange = (Calendar.getInstance().get(Calendar.YEAR) - 100)..Calendar.getInstance()
        .get(Calendar.YEAR),
) {
    val transitionState = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    fun dismissWithAnimation() {
        transitionState.targetState = false
    }

    LaunchedEffect(transitionState.currentState, transitionState.isIdle) {
        if (!transitionState.currentState && transitionState.isIdle) {
            onDismissRequest()
        }
    }

    val years = remember { yearRange.toList() }
    val initialIndex = remember {
        val index = initialYear?.let { years.indexOf(it) } ?: -1
        if (index >= 0) index else years.size - 1
    }
    val gridState = rememberLazyGridState(initialFirstVisibleItemIndex = initialIndex)

    Dialog(onDismissRequest = { dismissWithAnimation() }) {
        AnimatedVisibility(
            visibleState = transitionState,
            enter = fadeIn(tween(300)) + scaleIn(tween(300), initialScale = 0.8f),
            exit = fadeOut(tween(200)) + scaleOut(tween(200), targetScale = 0.8f)
        ) {
            Surface(
                shape = AppTheme.shapes.extraLarge,
                color = AppTheme.colors.surface,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                ) {
                    Text(
                        text = stringResource(R.string.choose_year),
                        style = AppTheme.typography.titleLarge,
                        color = AppTheme.colors.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = gridState,
                        modifier = Modifier.heightIn(max = 300.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(years) { year ->
                            val isSelected = year == initialYear

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) AppTheme.colors.primary
                                        else AppTheme.colors.surface
                                    )
                                    .clickable {
                                        onYearSelected(year)
                                        dismissWithAnimation()
                                    }
                            ) {
                                Text(
                                    text = year.toString(),
                                    style = AppTheme.typography.bodyLarge.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) AppTheme.colors.onPrimary
                                    else AppTheme.colors.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { dismissWithAnimation() }) {
                            Text(
                                text = stringResource(R.string.cancel),
                                style = AppTheme.typography.bodyLarge,
                                color = AppTheme.colors.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}