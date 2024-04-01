package presentation.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun BackIconButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onBackClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Back"
        )
    }
}