package feature.tree.ui.person_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erazero1.utils.DateFormatStyle
import com.erazero1.utils.format
import core.domain.model.Gender
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.components.AvatarCircle
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.showLongToast
import feature.tree.domain.model.Person
import feature.tree.ui.person_detail.components.AppBar
import feature.tree.ui.person_detail.components.EditPersonBottomSheet
import feature.tree.ui.person_detail.model.PersonDetailAction
import feature.tree.ui.person_detail.model.PersonDetailEvent
import feature.tree.ui.person_detail.model.PersonDetailState
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    personId: String,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<PersonDetailViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(personId) {
        viewModel.onEvent(PersonDetailEvent.LoadPerson(personId))
    }

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is PersonDetailAction.ShowToast -> {
                showLongToast(context, action.message)
            }
        }
    }
    val currentState = state.value
    if (currentState is PersonDetailState.Success && currentState.isEditing) {
        EditPersonBottomSheet(
            person = currentState.person,
            isLoading = currentState.isSaving,
            onSave = { request ->
                viewModel.onEvent(PersonDetailEvent.SubmitPersonEdit(personId, request))
            },
            onDismiss = {
                viewModel.onEvent(PersonDetailEvent.CloseEditDialog)
            }
        )
    }

    PersonDetailLayout(
        modifier = modifier,
        state = state.value,
        personId = personId,
        onBack = onBack,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun PersonDetailLayout(
    modifier: Modifier = Modifier,
    state: PersonDetailState,
    personId: String,
    onBack: () -> Unit,
    onEvent: (PersonDetailEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.surfaceBright,
        topBar = {
            AppBar(
                onEdit = { onEvent(PersonDetailEvent.OpenEditDialog) },
                onBack = onBack,
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                PersonDetailState.Initial -> Unit
                PersonDetailState.Loading -> LoadingCard(Modifier.fillMaxSize())
                is PersonDetailState.Error -> ErrorCard(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message ?: stringResource(R.string.unknown_error),
                    onTryAgainClick = { onEvent(PersonDetailEvent.LoadPerson(personId)) }
                )

                is PersonDetailState.Success -> PersonDetailContent(
                    person = state.person,
                )
            }
        }
    }
}

@Composable
private fun PersonDetailContent(
    modifier: Modifier = Modifier,
    person: Person,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            PersonHeader(
                modifier = Modifier.padding(top = 8.dp),
                person = person
            )
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { PersonAttributes(person) }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item { LifeEvents(person) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { Biography(person) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { PersonFooter(person) }
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun PersonHeader(
    modifier: Modifier = Modifier,
    person: Person,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarCircle(
            modifier = Modifier.size(128.dp),
            avatarUrl = person.photoUrl,
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${person.firstName} ${person.patronymic} ${person.lastName}".trim(),
            style = AppTheme.typography.headlineSmall,
            color = AppTheme.colors.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        val lifeYears = person.birthDate.atZone(ZoneId.systemDefault()).year.toString() +
                " - " +
                if (person.isAlive) stringResource(R.string.present)
                else person.deathDate.atZone(ZoneId.systemDefault()).year.toString()

        Text(
            text = lifeYears,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}

@Composable
private fun PersonAttributes(person: Person) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AttributeCard(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.gender).uppercase(),
            value = person.gender.name.lowercase().replaceFirstChar { it.uppercase() },
            iconRes = when (person.gender) {
                Gender.MALE -> R.drawable.ic_male
                Gender.FEMALE -> R.drawable.ic_female
                Gender.OTHER -> R.drawable.ic_ellipsis_horiz
            },
            iconTint = when (person.gender) {
                Gender.MALE -> AppTheme.colors.male
                Gender.FEMALE -> AppTheme.colors.female
                Gender.OTHER -> AppTheme.colors.outline
            }
        )
        AttributeCard(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.status).uppercase(),
            value = stringResource(if (person.isAlive) R.string.alive else R.string.deceased),
            iconRes = R.drawable.ic_adjust,
            iconTint = if (person.isAlive) AppTheme.colors.success else AppTheme.colors.error
        )
    }
}

@Composable
private fun AttributeCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    iconRes: Int,
    iconTint: Color = AppTheme.colors.onSurfaceVariant,
) {
    Card(
        modifier = modifier,
        shape = AppTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun LifeEvents(person: Person) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.life_events),
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            TimelineMarker(person.isAlive)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                LifeEventCard(
                    title = stringResource(R.string.born),
                    location = person.birthPlace,
                    date = person.birthDate.format(DateFormatStyle.DD_MMMM_YYYY) ?: ""
                )
                if (!person.isAlive) {
                    LifeEventCard(
                        title = stringResource(R.string.died),
                        location = person.deathPlace,
                        date = person.deathDate.format(DateFormatStyle.DD_MMMM_YYYY) ?: ""
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineMarker(isAlive: Boolean) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .height(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location_on),
            contentDescription = null,
            tint = AppTheme.colors.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        val lineColor = AppTheme.colors.outline
        Canvas(
            Modifier
                .width(1.dp)
                .fillMaxHeight()
        ) {
            drawLine(
                color = lineColor,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = 2f,
                pathEffect = pathEffect
            )
        }
        if (!isAlive) {
            Icon(
                painter = painterResource(id = R.drawable.ic_deceased),
                contentDescription = null,
                tint = AppTheme.colors.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun LifeEventCard(title: String, location: String, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        shape = AppTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = AppTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(text = location, style = AppTheme.typography.bodyMedium)
            }
            Text(
                text = date,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun Biography(person: Person) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = AppTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.biography),
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colors.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = person.biography,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun PersonFooter(person: Person) {
    val createdDate = person.createdAt
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH))
        .uppercase()

    Text(
        text = "CREATED: $createdDate | ID: ${person.id}",
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colors.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
}
