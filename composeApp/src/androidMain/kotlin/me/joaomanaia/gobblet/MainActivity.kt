package me.joaomanaia.gobblet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.presentation.theme.GobbletTheme
import di.gameModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import presentation.game.GameScreen
import presentation.game.GameScreenViewModel

class A : ViewModel() {
    val a = viewModelScope.coroutineContext
}

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(gameModule)
        }

        setContent {
            GobbletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(
                        viewModel = gameViewModel
                    )
                }
            }
        }
    }
}
