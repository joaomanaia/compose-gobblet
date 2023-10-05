package presentation.game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.GobbletTier
import model.Player
import presentation.game.components.GobbletComponent

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GameScreen() {
    val (tierScale, setTierSize) = remember {
        mutableFloatStateOf(1f)
    }

    val tierSize by animateDpAsState(
        targetValue = 64.dp * tierScale
    )

    Scaffold {
        Column {
            Slider(
                value = tierScale,
                onValueChange = setTierSize,
                valueRange = 0.5f..5f,
                steps = 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                GobbletTier.entries.forEach { tier ->
                    Player.entries.forEach { player ->
                        GobbletComponent(
                            tier = tier,
                            player = player,
                            size = tierSize,
                        )
                    }
                }
            }
        }
    }
}
